package pfq.storage.server.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pfq.storage.server.dao.RoleDAO;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;
import pfq.storage.server.utils.AppUtil;
import pfq.storage.server.utils.PFQloger;

@Repository
public class RoleDaoImpl implements RoleDAO {
	
    private Logger logger = PFQloger.getLogger(RoleDAO.class,Level.ALL);
    
    private Role tmp ;
    
    @Autowired
    MongoOperations mongoOperation;


	@Override
	public boolean addRole(Role role) {
        logger.debug("addRole");
		boolean result = !checkHasRoleByID(role.getId()) ? !checkHasRole(role.getName()) : false;
		if (result) {
			mongoOperation.save(role);
		} else {
			AppUtil.setError("Role has on server !!!");
		}
		return result;
	}

	@Override
	public boolean deleteRole(Role role) {
        logger.debug("deleteRole");
		boolean result = false;
		BasicQuery querycargo = new BasicQuery(
				"{$or:[{_id:'" + role.getId() + "'}," + "{name:'" + role.getName() + "'}]}");
		tmp = mongoOperation.findOne(querycargo, Role.class);
		if (tmp != null) {
			result = true;
			mongoOperation.remove(tmp);
		}

		return result;
	}

	@Override
	public boolean checkHasRole(String name) {
        logger.debug("checkHasRole");
        Boolean result = false;
		BasicQuery querycargo = new BasicQuery(
				"{$or:[{name:'" + name + "'}]}");
		tmp = mongoOperation.findOne(querycargo, Role.class);
		
		result = tmp!=null?true:false; 
	    return result;
	}

	@Override
	public boolean checkHasRoleByID(String id) {
        logger.debug("checkHasRoleByID");
        Boolean result = false;
		BasicQuery querycargo = new BasicQuery(
				"{$or:[{_id:'" + id + "'}]}");
		tmp = mongoOperation.findOne(querycargo, Role.class);
		
		result = tmp!=null?true:false; 
	    return result;
	}

	@Override
	public Optional<Role> findRole(String name) {
        logger.debug("findRole");
		BasicQuery querycargo = new BasicQuery("{$or:[{name:'" + name + "'}]}");
		tmp = mongoOperation.findOne(querycargo, Role.class);
		return Optional.of(tmp);
	}

	@Override
	public Optional<Role> findRoleByID(String id) {
        logger.debug("findRoleByID");
		BasicQuery querycargo = new BasicQuery("{$or:[{_id:'" + id + "'}]}");
		tmp = mongoOperation.findOne(querycargo, Role.class);
		return Optional.of(tmp);
	}

	@Override
	public Optional<Role> findRoleByQueryOne(String query) {
        logger.debug("findRoleByQueryOne");
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, Role.class);
		return Optional.of(tmp);
	}

	@Override
	public List<Role> findRoleByQueryList(String query) {
        logger.debug("findRoleByQueryList");
		BasicQuery querycargo = new BasicQuery(query);
		List<Role> ltmp = mongoOperation.find(querycargo, Role.class);
		return ltmp;
	}

	@Override
	public List<Role> findRoleByQueryList(Query query) {
        logger.debug("findRoleByQueryList");
		List<Role> ltmp = mongoOperation.find(query, Role.class);
		return ltmp;
	}

	@Override
	public List<User> getListUser(String name) {
        logger.debug("getListUser");
		return findRole(name).get().getUsers();
	}

	@Override
	public List<User> getListUserByID(String id) {
        logger.debug("getListUserByID");
        return findRoleByID(id).get().getUsers();
	}

	@Override
	public List<Role> getAllRole() {
        logger.debug("getAllRole");
		return mongoOperation.findAll(Role.class);
	}

	@Override
	public Optional<User> setUser(String role_name,User u) {
		findRole(role_name);
		tmp.setUsers(u);
		return null;
	}

	@Override
	public boolean editRole(Role role) {
		boolean result = false;
		BasicQuery querycargo = new BasicQuery(
				"{$or:[{_id:'" + role.getId() + "'}," + "{name:'" + role.getName() + "'}]}");
		if (mongoOperation.findOne(querycargo, Role.class) != null) {
			result = true;
			mongoOperation.save(role);
		}
		return result;
	}

}
