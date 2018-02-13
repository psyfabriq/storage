package pfq.storage.server.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.File;
import pfq.storage.server.model.Folder;
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
		Folder f;
		if(map.containsKey("parrent")) {
			Optional<Folder>  fp = fileDAO.findFolder((String)map.get("parrent"));
			if (fp.isPresent()) {
				f = Folder.newBuilder().setName((String) map.get("name")).setParrent(fp.get())
						.setPath(fp.get().getPath() + OSValidator.getOSSeparator() + (String) map.get("name")).build();
			}else {
				return AppUtil.getResponseJson("Coud not add folder , parrent folder not found ",ResponseStatus.ERROR);
			}
		} else {
			f = Folder.newBuilder().setName((String) map.get("name"))
					.setPath(OSValidator.getOSSeparator() + (String) map.get("name")).build();

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
		// TODO Auto-generated method stub
		return null;
	}

}
