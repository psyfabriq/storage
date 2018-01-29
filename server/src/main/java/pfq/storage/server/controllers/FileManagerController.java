package pfq.storage.server.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pfq.storage.server.AppUtil;

@RestController
@RequestMapping("/file/api/")
public class FileManagerController {
        private static final HttpHeaders head = new HttpHeaders();
        private Map<String, Object> map;
	
	    @RequestMapping(value = "/get-file-list", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
		public String getFileList(String json, HttpServletResponse response) {
		//	map = AppUtil.getValues(json);
			String s = " {\"result\":[{\"name\":\"DSCF4215s.JPG\",\"rights\":\"-rw-r--r--\",\"size\":124058,\"time\":\"2016-11-14 16:33:24\",\"type\":\"file\",\"isimage\":true,\"url\":\"..\\/images_public\\/contentfolder\\/DSCF4215s.JPG\"},{\"name\":\"donate\",\"rights\":\"drwxrwxr-x\",\"size\":0,\"time\":\"2016-11-13 12:27:34\",\"type\":\"dir\"},{\"name\":\"hram.jpg\",\"rights\":\"-rw-r--r--\",\"size\":13613,\"time\":\"2016-07-14 15:26:55\",\"type\":\"file\",\"isimage\":true,\"url\":\"..\\/images_public\\/contentfolder\\/hram.jpg\"},{\"name\":\"ib693.jpg\",\"rights\":\"-rw-r--r--\",\"size\":50084,\"time\":\"2016-07-14 15:36:47\",\"type\":\"file\",\"isimage\":true,\"url\":\"..\\/images_public\\/contentfolder\\/ib693.jpg\"},{\"name\":\"stagesofconstruction\",\"rights\":\"drwxrwxr-x\",\"size\":0,\"time\":\"2016-07-15 12:13:32\",\"type\":\"dir\"},{\"name\":\"underconstruction.png\",\"rights\":\"-rw-r--r--\",\"size\":119073,\"time\":\"2016-07-19 15:01:11\",\"type\":\"file\",\"isimage\":true,\"url\":\"..\\/images_public\\/contentfolder\\/underconstruction.png\"}]}";
			return s;
		}
	    

}
