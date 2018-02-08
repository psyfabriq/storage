package pfq.storage.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import pfq.storage.server.utils.ApplicationProperties;
import pfq.storage.server.utils.OSValidator;

@Component
@PropertySource(value = "classpath:application.properties")
public class PostConstructExampleBean {
 
    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private RoleDAO roleDao;
    
	@Value("${pfq.paths.uploadedFiles}")
	private String uploadFolder;
	
	@Autowired
	ApplicationProperties applicationProperties;
 
    @PostConstruct
    public void init() {
    
    	Role ru;
    	Role ra;

    	checkUploadFolder();
    	
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
    
    private void checkUploadFolder() {
    	
    	if (!Files.exists(Paths.get(uploadFolder))) {
			try {
				Files.createDirectories(Paths.get(uploadFolder));
			} catch (IOException e1) {
				//System.out.println(OSValidator.getCurrentSystemUser());
				System.out.println(OSValidator.getCurrentSystemUserHome());
				applicationProperties.setProperty("pfq.paths.uploadedFiles", OSValidator.getCurrentSystemUserHome()
						                                                     +OSValidator.getOSSeparator()
						                                                     +"pfq"
						                                                     +OSValidator.getOSSeparator()
						                                                     +"upload");
				applicationProperties.save();
				uploadFolder = applicationProperties.getProperty("pfq.paths.uploadedFiles");
				checkUploadFolder();
				//e1.printStackTrace();
			}
		}
    }
}
