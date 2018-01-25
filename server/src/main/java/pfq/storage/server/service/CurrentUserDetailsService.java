package pfq.storage.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.model.User;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userService.getUserByLogin(login).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with login=%s was not found", login)));
		return new CurrentUser(user);
	}

}
