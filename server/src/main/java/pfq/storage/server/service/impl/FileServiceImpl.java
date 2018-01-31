package pfq.storage.server.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.File;
import pfq.storage.server.service.FileService;

@Service
public class FileServiceImpl  implements FileService{
	
	@Autowired
	FileDAO fileDAO;

	@Override
	public String addFolder(Map<String, Object> map) {
		//File f = File.newBuilder().se
		return null;
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
