package pfq.storage.server.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pfq.storage.server.PFQloger;
import pfq.storage.server.controllers.FileManagerControllerI;

@RestController
@RequestMapping("/file/api/")
public class FileManagerController implements FileManagerControllerI {
	
	private Logger logger = PFQloger.getLogger(FileManagerController.class, Level.ALL);
	private static final HttpHeaders head = new HttpHeaders();
	private Map<String, Object> map;

	public FileManagerController() {
		super();
		head.add("Content-type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
	}

	@Override
	public ResponseEntity<String> addFolder(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemCopy(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemDelete(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemUpload(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemDownload(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemCompress(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> itemMoveFile(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseEntity<String> itemRenameFile(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity<String> getListDirectory(String json, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return new ResponseEntity<String>("test", head,HttpStatus.OK);
	}

}
