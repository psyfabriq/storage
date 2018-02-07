package pfq.storage.server.controllers.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pfq.storage.server.AppUtil;
import pfq.storage.server.PFQloger;
import pfq.storage.server.controllers.AdminRestApiControllerI;
import pfq.storage.server.model.User;
import pfq.storage.server.service.UserService;
 
 
@RestController
@RequestMapping("/admin/api/")
public class AdminRestApiController implements AdminRestApiControllerI {
	
    private Logger logger = PFQloger.getLogger(AdminRestApiController.class, Level.ALL);
    private static final HttpHeaders head = new HttpHeaders();
    private Map<String, Object> map;
    
    @Autowired
    private UserService userService;
    
    public AdminRestApiController() {
        super();
        head.add("Content-type",MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
    }
	
    
    /*
     * @see /all-users-get GET No Parameters
     */
    @Override
    public @ResponseBody List<User> allUser(ModelMap model) {
        return userService.listUser();
    }

     /*
     * @see /add-user POST {"login":"######","email":"#####","name":"######","password":"#####"}
     */
	@Override
	public ResponseEntity<String> addUser(@RequestBody String json, HttpServletResponse response) {
		
		System.out.println("addUser");
		map = AppUtil.getValues(json);
		return new ResponseEntity<String>(userService.add(map), head,HttpStatus.OK);
	}

    /*
    * @see /rm-user POST {"login":"######","email":"#####"}
    */
	@Override
	public ResponseEntity<String> removeUser(@RequestBody String json, HttpServletResponse response) {
		map = AppUtil.getValues(json);
		return new ResponseEntity<String>(userService.remove(map), head,HttpStatus.OK);
	}

    /*
    * @see /edit-user POST {"login":"######","email":"#####","name":"######","password":"#####","old_email:"#####", "old_password":"#####", "old_login":"#####", "old_name":"#####"}
    */
	@Override
	public ResponseEntity<String> editUser(@RequestBody String json, HttpServletResponse response) {
		map = AppUtil.getValues(json);
		return new ResponseEntity<String>(userService.edit(map), head,HttpStatus.OK);
	}

    /*
    * @see /get-user POST {"login":"######","email":"#####"}
    */
	@Override
	public ResponseEntity<String> getUser(@RequestBody String json, HttpServletResponse response) {
		map = AppUtil.getValues(json);
		System.out.println(map);

		return new ResponseEntity<String>(userService.getUser(map), head,HttpStatus.OK);
		//return new ResponseEntity<String>("test", head,HttpStatus.OK);
	}

}
