package pfq.storage.server.controllers.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pfq.storage.server.controllers.FileManagerControllerI;
import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.service.FileService;
import pfq.storage.server.service.SystemInfoService;
import pfq.storage.server.utils.AppUtil;
import pfq.storage.server.utils.PFQloger;

@RestController
@RequestMapping("/file/api/")
public class FileManagerController implements FileManagerControllerI {

	private Logger logger = PFQloger.getLogger(FileManagerController.class, Level.ALL);
	private static final HttpHeaders head = new HttpHeaders();
	private Map<String, Object> map;
	private String result = "{}";

	@Autowired
	SystemInfoService systemInfoService;

	@Autowired
	FileService fileService;

	public FileManagerController() {
		super();
		head.add("Content-type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
	}

	private void prepare(String json, HttpServletRequest request) {
		map = AppUtil.getValues(json);
	    systemInfoService.getCurrentUser(request);
	    System.out.println(map);
		
	}

	@Override
	public ResponseEntity<String> addFolder(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.addFolder(map);
		}
		return new ResponseEntity<String>(result, head, systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemCopy(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemCopy(map); 
		}
		return new ResponseEntity<String>(result, head, systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override ///item-delete
	public ResponseEntity<String> itemDelete(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemDelete(map); 
		}
		return new ResponseEntity<String>(result, head, systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemUpload(@RequestParam("json") String json, @RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println(json);
		System.out.println(file);
		System.out.println("3453453453453453453453453453453453453453453454");
		
		prepare(json, request);
		
		if (systemInfoService.access()) {
			result = fileService.itemUpload(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemDownload(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemDownload(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemCompress(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemCompress(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemMoveFile(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemMoveFile(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> itemRenameFile(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.itemRenameFile(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<String> getListDirectory(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(json);
		prepare(json, request);
		if (systemInfoService.access()) {
			result = fileService.getListDirectory(map); 
		}
		return new ResponseEntity<String>(result, head,
				systemInfoService.access() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
	}

}
