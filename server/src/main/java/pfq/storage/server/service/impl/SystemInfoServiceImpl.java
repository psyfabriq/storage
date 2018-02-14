package pfq.storage.server.service.impl;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfq.storage.server.PostConstructExampleBean;
import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.model.User;
import pfq.storage.server.service.SystemInfoService;
import pfq.storage.server.utils.OSValidator;

@Service
public class SystemInfoServiceImpl implements SystemInfoService{
	
	private Principal principal;
	private CurrentUser currentUser;
	
	@Autowired
    UserDAO userDao;
	
	@Autowired
	PostConstructExampleBean PCEB;
	
	
	@Override
	public CurrentUser getCurrentUser(HttpServletRequest request) {
		this.principal = request.getUserPrincipal();
		if (access()) {
			Optional<User> ou = userDao.findUser(principal.getName());
			this.currentUser = new CurrentUser(ou.get());
			return currentUser;
		}else { return null;}
	}


	@Override
	public String getCurrentUserFolder() {
		return PCEB.getUploadFolder()+OSValidator.getOSSeparator()+currentUser.getWorkFolder();
	}
	
	@Override
	public String getWorkFolder() {
		return OSValidator.getOSSeparator()+currentUser.getWorkFolder();
	}


	@Override
	public String getLocalPath() {
		return PCEB.getUploadFolder()+OSValidator.getOSSeparator();
	}
	
	@Override
	public String getCurrentUserID() {
		return currentUser.getId();
	}

	@Override
	public boolean access() {
		return this.principal!=null?true:false;
	}




}
