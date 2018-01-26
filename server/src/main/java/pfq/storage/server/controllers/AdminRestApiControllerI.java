package pfq.storage.server.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pfq.storage.server.model.User;

public interface AdminRestApiControllerI {
	
    @RequestMapping(value="/all-users-get", method = RequestMethod.GET)
    public @ResponseBody List<User> allUser(ModelMap model);
    
    @RequestMapping(value = "/add-user", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/rm-user", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeUser(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/edit-user", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> editUser(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-user", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getUser(@RequestBody String json, HttpServletResponse response);
}
