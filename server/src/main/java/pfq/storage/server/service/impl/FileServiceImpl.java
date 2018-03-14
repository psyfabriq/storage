package pfq.storage.server.service.impl;


import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.FileMO;
import pfq.storage.server.model.Folder;
import pfq.storage.server.model.exception.FileBuilderException;
import pfq.storage.server.service.FileService;
import pfq.storage.server.service.SystemInfoService;
import pfq.storage.server.utils.AppUtil;
import pfq.storage.server.utils.ImageUtill;
import pfq.storage.server.utils.OSValidator;
import pfq.storage.server.utils.ResponseStatus;

@Service
public class FileServiceImpl  implements FileService{
	
	@Autowired
	FileDAO fileDAO;
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	OcrService ocrService;
    
    

	@Override
	public String addFolder(Map<String, Object> map) {
		Folder f = null;
		if(map.containsKey("parrent")) {
			if("".equals(map.get("parrent").toString()) || "/".equals(map.get("parrent").toString())){
				map.remove("parrent");
			}
		}
		if(map.containsKey("parrent")) {
			Optional<Folder>  fp = fileDAO.findFolder((String)map.get("parrent"));
			if (fp.isPresent()) {
				try {
					f = Folder.newBuilder()
							.setName((String) map.get("name"))
							.setUserId(systemInfoService.getCurrentUserID())
							.setParrent(fp.get())
						    .setPath(fp.get().getPath() + OSValidator.getOSSeparator() + AppUtil.toTranslitLat((String) map.get("name"), true)).build();
				} catch (FileBuilderException e) {
					return AppUtil.getResponseJson(e.toString(),ResponseStatus.ERROR);
				}
			}else {
				return AppUtil.getResponseJson("Coud not add folder , parrent folder not found ",ResponseStatus.ERROR);
			}
		} else {
			try {
				f = Folder.newBuilder()
						.setName((String) map.get("name"))
						.setUserId(systemInfoService.getCurrentUserID())
						.setPath(OSValidator.getOSSeparator()+ AppUtil.toTranslitLat((String) map.get("name"), true)).build();
			} catch (FileBuilderException e) {
				return AppUtil.getResponseJson(e.toString(),ResponseStatus.ERROR);
			}

		}
		return AppUtil.getResponseJson(fileDAO.addFolder(f));
	}

	@Override
	public String itemCopy(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String itemDelete(Map<String, Object> map) {
		boolean isDir = false;
		if(!map.containsKey("type") || !map.containsKey("id")) {
			return AppUtil.getResponseJson("Not set all parametrs",ResponseStatus.ERROR);
		}
		isDir = "dir".equals((String)map.get("type"))?true:false;
		
		if(isDir) {
			Optional<Folder> foldertodelete = fileDAO.findFolderID((String) map.get("id"));
			if(foldertodelete.isPresent()) {
				Queue<Folder> fileTree = new PriorityQueue<>();
				Collections.addAll(fileTree, foldertodelete.get());
				while (!fileTree.isEmpty()) {
					Folder currentFolder = fileTree.remove();
					List<Folder> childFolders = fileDAO.getAllFolders(currentFolder);
					for (Folder folder : childFolders) {
						Collections.addAll(fileTree, folder);
					}
					
					System.out.println(currentFolder.getPath()+" is remove "+fileDAO.deleteFolder(currentFolder));
				}

			}else {
				return AppUtil.getResponseJson("Not Found folder to delete",ResponseStatus.ERROR);
			}
			
		}else {
			Optional<FileMO> filetodelete = fileDAO.findFileID((String) map.get("id"));
			if(filetodelete.isPresent()) {
				String destination = systemInfoService.getCurrentUserFolder() +OSValidator.getOSSeparator()+ filetodelete.get().getId();
				File file = new File(destination);
	    		if(file.delete()){
	    			System.out.println(file.getName() + " is deleted!");
	    			fileDAO.deleteFile(filetodelete.get());
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    		}
			}
		}
		
	 return AppUtil.getResponseJson("GOOD",ResponseStatus.OK);
	}

	@Override
	public String itemUpload(Map<String, Object> map,MultipartFile mfile) {
		FileMO f = null;
		if(map.containsKey("parrent")) {
			if("".equals(map.get("parrent").toString()) || "/".equals(map.get("parrent").toString())){
				map.remove("parrent");
			}
		}
		if(map.containsKey("parrent")) {
			Optional<Folder>  fp = fileDAO.findFolder((String)map.get("parrent"));
			if(fp.isPresent() && map.containsKey("name")) {
				  try {
					f =  FileMO.newBuilder()
						       .setName((String)map.get("name"))
						       .setUserId(systemInfoService.getCurrentUserID())
						       .setParrent(fp.get())
						       .setPath(fp.get().getPath() + OSValidator.getOSSeparator() + AppUtil.toTranslitLat((String) map.get("name"), true))
						       .build();
				} catch (FileBuilderException e) {
					return AppUtil.getResponseJson(e.toString(),ResponseStatus.ERROR);
				}
			}else {
				return AppUtil.getResponseJson("Coud not upload file , parrent folder not found ",ResponseStatus.ERROR);
			}
			
		}else {
			
			try {
				f = FileMO.newBuilder()
						.setName((String) map.get("name"))
						.setUserId(systemInfoService.getCurrentUserID())
						.setPath(OSValidator.getOSSeparator()+ AppUtil.toTranslitLat((String) map.get("name"), true))
						.build();
			} catch (FileBuilderException e) {
				return AppUtil.getResponseJson(e.toString(),ResponseStatus.ERROR);
			}
			
		}
		boolean result = fileDAO.addFile(f);
		if(result)
		{
			Optional<FileMO> fo = fileDAO.findFile(f.getPath());
		    String destination = systemInfoService.getCurrentUserFolder() +OSValidator.getOSSeparator()+ fo.get().getId();
		    File file = new File(destination);
		    try {
				mfile.transferTo(file);
				
				String mimeType  = URLConnection.guessContentTypeFromName(file.getName());
				double size      = (file.length() / 1024)/1024; //mb
				
				f = f.getBuilder().setMime(mimeType).setSize(size).build();
				fileDAO.editFile(f);
				
			} catch (IllegalStateException | IOException | FileBuilderException e) {
				result = false;
				fileDAO.deleteFile(f);
				e.printStackTrace();
			}
		    try {
		    	System.out.println(ocrService.read(ImageUtill.doIt(file)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(ocrService.read(file));
		}
		

		
		return AppUtil.getResponseJson(result);
	}

	@Override
	public Optional<File> itemDownload(Map<String, Object> map) {
		if(map.containsKey("id")) {
				String destination = systemInfoService.getCurrentUserFolder() +OSValidator.getOSSeparator()+ (String)map.get("id");
				return Optional.ofNullable(new File(destination));
		}	
		return Optional.ofNullable(null);
	}
	
	@Override
	public String itemDownloadProp(Map<String, Object> map) {
		if(!map.containsKey("id")) {
			return AppUtil.getResponseJson("Not set all parametrs",ResponseStatus.ERROR);
		}
		Optional<FileMO> filetodownload = fileDAO.findFileID((String) map.get("id"));
		if(filetodownload.isPresent()) {
			return AppUtil.getResponseJson(filetodownload.get(), ResponseStatus.OK);
		}else {
			return AppUtil.getResponseJson("Not Found file to download",ResponseStatus.ERROR);
		}
		//return null;
	}
	
	/*
	 * 		if(map.containsKey("id")) {
			Optional<FileMO> filetodownload = fileDAO.findFileID((String) map.get("id"));
			if(filetodownload.isPresent()) {
				FileMO fmo = filetodownload.get();
				String destination = systemInfoService.getCurrentUserFolder() +OSValidator.getOSSeparator()+ fmo.getId();
				fmo.setTmpFile(new File(destination));
				return Optional.ofNullable(new File(destination));
			}
		}	return Optional.ofNullable(null);
	 */

	@Override
	public String itemCompress(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String itemMoveFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String itemRenameFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListDirectory(Map<String, Object> map) {

		Map<String, Object> result = new HashMap<String, Object>();
		if(map.containsKey("parrent")) {
			if("/".equals((String)map.get("parrent"))) {
				result.put("folders", fileDAO.getAllFolders());
				result.put("files",   fileDAO.getAllFiles());
			} else {
				Optional<Folder> parrent = fileDAO.findFolder((String) map.get("parrent"));
				if (parrent.isPresent()) {
					result.put("folders", fileDAO.getAllFolders(parrent.get()));
					result.put("files",   fileDAO.getAllFiles(parrent.get()));
				} else {
					return AppUtil.getResponseJson("Parrent folder not found ", ResponseStatus.ERROR);
				}
			}
			
		}else {
			result.put("folders", fileDAO.getAllFolders());
		}
		
		return AppUtil.getResponseJson(result,ResponseStatus.OK);

	}





}
