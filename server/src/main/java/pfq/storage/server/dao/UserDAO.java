package pfq.storage.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;


public interface UserDAO {
    boolean addUser(User user);
    boolean editUser(User user);
    boolean deleteUser(User user);
    boolean checkHasUser(String login);
    boolean checkHasUser(String login,String email);
    boolean checkHasUser(String login,String email, String neid);
    boolean checkHasUserByID(String ID);
    Optional<User>  findUser(String login);
    Optional<User>  findUser(String login,String email);
    Optional<User>  findUserByID(String id);
    //Optional<User>  findUserByEmail (String email);
    //Optional<User>  findUserByQueryOne(String query);
    //List<User> findUserByQueryList(String query);
    //List<User> findUserByQueryList(Query query);
    List<Role> getListRole(String login);
    List<Role> getListRoleByID(String id);
    List<User> getAllUser();
}
