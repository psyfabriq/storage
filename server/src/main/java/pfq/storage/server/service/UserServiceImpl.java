package pfq.storage.server.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.PFQloger;
import pfq.storage.server.dao.RoleDAO;
import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.User;

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
		return null;
	}

	@Override
	public String remove(Map<String, Object> map) {
        logger.debug("remove");
		return null;
	}

	@Override
	public String getUser(Map<String, Object> map) {
        logger.debug("getUser");
		return null;
	}

	@Override
	public String getAllUser(Map<String, Object> map) {
        logger.debug("getAllUser");
		return null;
	}

	@Override
	public List<User> listUser() {
        logger.debug("listUser");
		return null;
	}
}
