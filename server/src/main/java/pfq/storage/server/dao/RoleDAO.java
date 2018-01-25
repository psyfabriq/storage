package pfq.storage.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;

public interface RoleDAO {
	    boolean addRole(Role role);
	    boolean editRole(Role role);
	    boolean deleteRole(Role role);
	    boolean checkHasRole(String name);
	    boolean checkHasRoleByID(String id);
	    Optional<Role> findRole(String name);
	    Optional<Role> findRoleByID(String id);
	    Optional<Role> findRoleByQueryOne(String query);
	    List<Role> findRoleByQueryList(String query);
	    List<Role> findRoleByQueryList(Query query);
	    List<User> getListUser(String name);
	    List<User> getListUserByID(String id);
	    List<Role> getAllRole();
	    Optional<User> setUser(String role_name, User u);
}
