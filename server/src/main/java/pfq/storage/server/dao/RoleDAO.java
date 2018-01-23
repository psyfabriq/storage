package pfq.storage.server.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

public interface RoleDAO {
	    boolean addRole(Role role);
	    boolean editRole(Role role);
	    boolean deleteRole(Role role);
	    boolean checkHasRole(String name);
	    boolean checkHasRoleByID(String id);
	    User findRole(String name);
	    User findRoleByID(String id);
	    User findRoleByQueryOne(String query);
	    List<User> findRoleByQueryList(String query);
	    List<User> findRoleByQueryList(Query query);
	    List<Role> getListUser(String name);
	    List<Role> getListUserByID(String id);
	    List<User> getAllRole();
}
