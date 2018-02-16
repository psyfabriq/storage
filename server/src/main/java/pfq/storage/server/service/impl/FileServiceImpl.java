package pfq.storage.server.service.impl;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.File;
import pfq.storage.server.model.Folder;
import pfq.storage.server.model.exception.FileBuilderException;
import pfq.storage.server.service.FileService;
import pfq.storage.server.service.SystemInfoService;
import pfq.storage.server.utils.AppUtil;
import pfq.storage.server.utils.OSValidator;
import pfq.storage.server.utils.ResponseStatus;

@Service
public class FileServiceImpl  implements FileService{
	
	@Autowired
	FileDAO fileDAO;
	
	@Autowired
	SystemInfoService systemInfoService;

	@Override
	public String addFolder(Map<String, Object> map) {
		Folder f = null;
		if(map.containsKey("parrent")) {
			Optional<Folder>  fp = fileDAO.findFolder((String)map.get("parrent"));
			if (fp.isPresent()) {
				try {
					f = Folder.newBuilder()
							.setName((String) map.get("name"))
							.setUserId(systemInfoService.getCurrentUserID())
							.setParrent(fp.get())
						    .setPath(fp.get().getPath() + OSValidator.getOSSeparator() + (String) map.get("name")).build();
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
						.setPath(OSValidator.getOSSeparator()+ (String) map.get("name")).build();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String itemUpload(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String itemDownload(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

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
		//String parrent = !map.containsKey("parrent")?"null":(String)map.get("parrent");
		//map.put("userid", systemInfoService.getCurrentUserID());
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList<File> test = new ArrayList<File>();
		if(map.containsKey("parrent")) {
			if("/".equals((String)map.get("parrent"))) {
				result.put("folders", fileDAO.getAllFolders());
			} else {
				Optional<Folder> parrent = fileDAO.findFolder((String) map.get("parrent"));
				if (parrent.isPresent()) {
					result.put("folders", fileDAO.getAllFolders(parrent.get()));
				} else {
					return AppUtil.getResponseJson("Parrent folder not found ", ResponseStatus.ERROR);
				}
			}
			
		}else {
			result.put("folders", fileDAO.getAllFolders());
			//return AppUtil.getResponseJson(fileDAO.getAllFolders(),ResponseStatus.OK);
		}
		result.put("files", test);
		return AppUtil.getResponseJson(result,ResponseStatus.OK);

	}

}
