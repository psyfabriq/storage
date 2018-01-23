package pfq.storage.server.dao;

import java.util.List;
import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;


public interface UserDAO {
    boolean addUser(User user);
    boolean editUser(User user);
    boolean deleteUser(User user);
    boolean checkHasUser(String login);
    boolean checkHasUserByID(String ID);
    User findUser(String login);
    User findUserByID(String id);
    User findUserByQueryOne(String query);
    List<User> findUserByQueryList(String query);
    List<User> findUserByQueryList(Query query);
    List<Role> getListRole(String login);
    List<Role> getListRoleByID(String id);
    List<User> getAllUser();
}
