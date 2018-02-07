package pfq.storage.server.service.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.AppUtil;
import pfq.storage.server.PFQloger;
import pfq.storage.server.ResponseStatus;
import pfq.storage.server.dao.RoleDAO;
import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.User;
import pfq.storage.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    private Logger logger = PFQloger.getLogger(UserService.class, Level.ALL);
	
	@Autowired
    UserDAO userDao;
	
	@Autowired
    RoleDAO roleDao;

	@Override
	public String add(Map<String, Object> map) {
        logger.debug("add");
        User u = User.newBuilder().setLogin((String) map.get("login"))
        		                  .setEmail((String) map.get("email"))
        		                  .setName((String) map.get("name"))
        		                  .setPassword((String) map.get("password"))
        		                  .setFoldercode(UUID.randomUUID().toString())
        		                  .setUserRoles(roleDao.findRole("USER").get())
        		                  .setIsActive(false)
        		                  .build();
		return AppUtil.getResponseJson(userDao.addUser(u));
	}
  
	@Override
	public String remove(Map<String, Object> map) {
        logger.debug("remove");
		return  AppUtil.getResponseJson(userDao.deleteUser(userDao.findUser((String) map.get("login")).get()));
	}
	
	@Override
	public String edit(Map<String, Object> map) {
		Optional<User> ou = userDao.findUserByID((String) map.get("id"));
		if(ou.isPresent()) {
			
		if(!userDao.checkHasUser((String)map.get("login"),(String)map.get("email"),(String)map.get("id"))) {
			User u = ou.get().getBuilder()
					.setLogin((String) map.get("login"))
	                .setEmail((String) map.get("email"))
	                .setName((String) map.get("name"))
	                .setPassword((String) map.get("password"))
	                .build();
			return AppUtil.getResponseJson(userDao.editUser(u));
		}else {
			return AppUtil.getResponseJson("Has another user with login or email !!",ResponseStatus.ERROR);
		}

		}else {
			return AppUtil.getResponseJson("Not found User !!",ResponseStatus.ERROR);
		}
	}

	@Override
	public String getUser(Map<String, Object> map) {
        logger.debug("getUser");
        StringBuilder result = new StringBuilder();
        System.out.println(map.get("login"));
        
        Optional<User> u = userDao.findUser((String)map.get("login"));
	
        if (u.isPresent()) {
        	Map<String, Object> mapo = new HashMap<String, Object>();
        	 //mapo.put("id", u.getId());
             mapo.put("login", u.get().getLogin());
             mapo.put("name", u.get().getName());
             mapo.put("email", u.get().getEmail());
             result.append(AppUtil.getResponseJson(mapo, ResponseStatus.OK));
        	
        }else {
        	result.append(AppUtil.getResponseJson("Not found User !!",ResponseStatus.ERROR));
        }
       
		return result.toString();
	}

	@Override
	public String getAllUser(Map<String, Object> map) {
        logger.debug("getAllUser");
		return null;
	}

	@Override
	public List<User> listUser() {
        logger.debug("listUser");
		return userDao.getAllUser();
	}

	@Override
	public Optional<User> getUserById(String id) {
		return userDao.findUserByID(id);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return null;
	}

	@Override
	public Optional<User> getUserByLogin(String login) {
		return userDao.findUser(login);
	}


}
