package pfq.storage.server;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pfq.storage.server.dao.RoleDAO;
import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

@Component
public class PostConstructExampleBean {
 
    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private RoleDAO roleDao;
 
    @PostConstruct
    public void init() {
    
    	Role ru;
    	Role ra;
    	
    	
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
			 
			 User au = User.newBuilder()
					  .setLogin("Admin")
					  .setName("Admin")
					  .setEmail("admin@system.com")
					  .setFoldercode(UUID.randomUUID().toString())
					  .setPassword("Admin")
					  .setUserRoles(ra)
					  .setUserRoles(ru)
					  .setIsActive(true)
					  .build();	
			 System.out.println(au.toString());
			 userDao.addUser(au);
			 ru.setUsers(au);
			 ra.setUsers(au);
			 roleDao.editRole(ra);
			 roleDao.editRole(ru);
		}
    }
}
