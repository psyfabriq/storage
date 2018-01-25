package pfq.storage.server;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

@Component
public class PostConstructExampleBean {
 
    @Autowired
    private UserDAO userDao;
 
    @PostConstruct
    public void init() {
    	if(!userDao.checkHasUser("Admin")) {
			 System.out.println("Not Check Admin User");
			 Role r = new Role();
			 r.setName(Role.Enum.ADMIN.toString());
			 User au = User.newBuilder()
					  .setLogin("Admin")
					  .setName("Admin")
					  .setEmail("admin@system.com")
					  .setPassword("Admin")
					  .setUserRoles(new Role().setName(Role.Enum.USER.toString()))
					  .setUserRoles(new Role().setName(Role.Enum.ADMIN.toString()))
					  .build();	
			 System.out.println(au.toString());
			 userDao.addUser(au);
		}
    }
}
