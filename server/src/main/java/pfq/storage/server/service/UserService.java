package pfq.storage.server.service;

import java.util.List;
import java.util.Map;

import pfq.storage.server.model.User;

public interface UserService {
    public String add(Map<String, Object> map);
    public String remove(Map<String, Object> map);
    public String getUser(Map<String, Object> map);
    public String getAllUser(Map<String, Object> map);
    public List<User> listUser();

}
