package pfq.storage.server.controllers;

import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pfq.storage.server.model.User;

public interface AdminRestApiControllerI {
    @RequestMapping(value="/all-users-get", method = RequestMethod.GET)
    public @ResponseBody List<User> allUser(ModelMap model);
}
