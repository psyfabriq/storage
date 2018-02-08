package pfq.storage.server;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import pfq.storage.server.dao.RoleDAO;
import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;
import pfq.storage.server.model.exception.UserBuildException;

@Component
@PropertySource(value = "classpath:application.properties")
public class PostConstructExampleBean {
 
    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private RoleDAO roleDao;
    
	@Value("${pfq.paths.uploadedFiles}")
	private String uploadFolder;
 
    @PostConstruct
    public void init() {
    
    	Role ru;
    	Role ra;
    	
    	File theDir = new File(uploadFolder);

    	if (!theDir.exists()) {
    	    System.out.println("creating directory: " + theDir.getName());
    	    try{
    	        theDir.mkdir();
    	        System.out.println("DIR created");
    	    } 
    	    catch(SecurityException se){
    	    	System.out.println(se.toString());
    	    }        

    	}
    	
    	if(!roleDao.checkHasRole(Role.Enum.USER.toString())) {
    		ru = new Role();
    		ru.setName(Role.Enum.USER.toString());
    		roleDao.addRole(ru);
    	}else {ru = roleDao.findRole(Role.Enum.USER.toString()).get();}
    	
    	if(!roleDao.checkHasRole(Role.Enum.ADMIN.toString())) {
    		ra = new Role();
    		ra.setName(Role.Enum.ADMIN.toString());
    		roleDao.addRole(ra);
    	}else {ra = roleDao.findRole(Role.Enum.ADMIN.toString()).get();}
    	
    	if(!userDao.checkHasUser("Admin")) {
			 System.out.println("Not Check Admin User");
			 
			 Role r = new Role();
			 r.setName(Role.Enum.ADMIN.toString());
			 
			 User au;
			try {
				
				au = User.newBuilder()
						  .setLogin("Admin")
						  .setName("Admin")
						  .setEmail("admin@system.com")
						  .setFoldercode(UUID.randomUUID().toString())
						  .setPassword("Admin")
						  .setUserRoles(ra)
						  .setUserRoles(ru)
						  .setIsActive(true)
						  .setIsAdministrative(true)
						  .build();
				 System.out.println(au.toString());
				 userDao.addUser(au);
				 ru.setUsers(au);
				 ra.setUsers(au);
				 roleDao.editRole(ra);
				 roleDao.editRole(ru);
				 
			} catch (UserBuildException e) {
				e.printStackTrace();
			}	
			
		}
    }
}
