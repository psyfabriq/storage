package pfq.storage.server.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public interface FileManagerControllerI {
	
    @RequestMapping(value = "/add-folder", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addFolder(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-copy", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemCopy(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-delete", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemDelete(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-upload", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemUpload(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-download", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemDownload(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-compress", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemCompress(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/item-edit-file", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> itemEditFile(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-list-directory", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getListDirectory(@RequestBody String json, HttpServletResponse response);
    

}
