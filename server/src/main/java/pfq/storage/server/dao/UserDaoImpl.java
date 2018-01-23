package pfq.storage.server.dao;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pfq.storage.server.AppUtil;
import pfq.storage.server.PFQloger;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

@Repository
public class UserDaoImpl implements UserDAO {
	
    private Logger logger = PFQloger.getLogger(UserDAO.class,Level.ALL);
    
    private User tmp ;
    
    @Autowired
    MongoOperations mongoOperation;


	@Override
	public boolean addUser(User user) {
        logger.debug("addUser");
        boolean result = !checkHasUserByID(user.getId())?!checkHasUser(user.getFoldercode()):false;
        if(result){
            mongoOperation.save(user);    
        } else{ AppUtil.setError("User has on server !!!");}
		return result;
	}

	@Override
	public boolean editUser(User user) {
        logger.debug("editUser");
        boolean result = false;
        
        BasicQuery querycargo = new BasicQuery("{$or:[{_id:'"+user.getId()+"'},"
                + "{login:'"+user.getLogin()+"'}]}");
        
        tmp = mongoOperation.findOne(querycargo, User.class);
        if(tmp != null){
            result=true;
        }
        
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
        logger.debug("deleteUser");
		return false;
	}

	@Override
	public boolean checkHasUser(String login) {
        logger.debug("checkHasUser");
		return false;
	}

	@Override
	public boolean checkHasUserByID(String ID) {
        logger.debug("checkHasUserByID");
		return false;
	}

	@Override
	public User findUser(String login) {
        logger.debug("findUser");
		return null;
	}

	@Override
	public User findUserByID(String id) {
        logger.debug("findUserByID");
		return null;
	}

	@Override
	public User findUserByQueryOne(String query) {
        logger.debug("findUserByQueryOne");
		return null;
	}

	@Override
	public List<User> findUserByQueryList(String query) {
        logger.debug("findUserByQueryList");
		return null;
	}

	@Override
	public List<User> findUserByQueryList(Query query) {
        logger.debug("findUserByQueryList");
		return null;
	}

	@Override
	public List<Role> getListRole(String login) {
        logger.debug("getListRole");
		return null;
	}

	@Override
	public List<Role> getListRoleByID(String id) {
        logger.debug("getListRoleByID");
		return null;
	}

	@Override
	public List<User> getAllUser() {
        logger.debug("getAllUser");
		return null;
	}

}
