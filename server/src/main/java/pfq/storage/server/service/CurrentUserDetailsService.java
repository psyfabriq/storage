package pfq.storage.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.model.User;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
	
	@Autowired
	private  UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		User user = userDao.findUser(arg0)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with login=%s was not found", arg0)));
        return new CurrentUser(user);
	}
}
