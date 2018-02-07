package pfq.storage.server.service.impl;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.service.SystemInfoService;

@Service
public class SystemInfoServiceImpl implements SystemInfoService{
	
	private Principal principal;
	
	@Autowired
    UserDAO userDao;
	
	
	@Override
	public CurrentUser getCurrentUser(HttpServletRequest request) {
		this.principal = request.getUserPrincipal();
		return new CurrentUser(userDao.findUser(principal.getName()).get());
	}

}
