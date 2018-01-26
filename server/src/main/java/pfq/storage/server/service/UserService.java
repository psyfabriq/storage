package pfq.storage.server.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import pfq.storage.server.model.User;

public interface UserService {
    public String add(Map<String, Object> map);
    public String remove(Map<String, Object> map);
    public String edit(Map<String, Object> map);
    public String getUser(Map<String, Object> map);
    public String getAllUser(Map<String, Object> map);
    public Optional<User>getUserById(String id);
    public Optional<User>getUserByEmail(String email); 
    public Optional<User>getUserByLogin(String login); 
    public List<User> listUser();

}
