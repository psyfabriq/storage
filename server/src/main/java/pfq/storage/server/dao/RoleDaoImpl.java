package pfq.storage.server.dao;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pfq.storage.server.PFQloger;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

@Repository
public class RoleDaoImpl implements RoleDAO {
	
    private Logger logger = PFQloger.getLogger(RoleDAO.class,Level.ALL);
    
    private Role tmp ;
    
    @Autowired
    MongoOperations mongoOperation;


	@Override
	public boolean addRole(Role role) {
        logger.debug("addRole");

		return false;
	}

	@Override
	public boolean editRole(Role role) {
        logger.debug("editRole");
		return false;
	}

	@Override
	public boolean deleteRole(Role role) {
        logger.debug("deleteRole");
		return false;
	}

	@Override
	public boolean checkHasRole(String name) {
        logger.debug("checkHasRole");
		return false;
	}

	@Override
	public boolean checkHasRoleByID(String id) {
        logger.debug("checkHasRoleByID");
		return false;
	}

	@Override
	public User findRole(String name) {
        logger.debug("findRole");
		return null;
	}

	@Override
	public User findRoleByID(String id) {
        logger.debug("findRoleByID");
		return null;
	}

	@Override
	public User findRoleByQueryOne(String query) {
        logger.debug("findRoleByQueryOne");
		return null;
	}

	@Override
	public List<User> findRoleByQueryList(String query) {
        logger.debug("findRoleByQueryList");
		return null;
	}

	@Override
	public List<User> findRoleByQueryList(Query query) {
        logger.debug("findRoleByQueryList");
		return null;
	}

	@Override
	public List<Role> getListUser(String name) {
        logger.debug("getListUser");
		return null;
	}

	@Override
	public List<Role> getListUserByID(String id) {
        logger.debug("getListUserByID");
		return null;
	}

	@Override
	public List<User> getAllRole() {
        logger.debug("getAllRole");
		return null;
	}

}
