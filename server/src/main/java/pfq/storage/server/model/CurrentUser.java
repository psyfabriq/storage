package pfq.storage.server.model;

import java.util.List;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getUserRolesArray()));
		this.user = user;
	}

	public String getId() {
		return user.getId();
	}

	public List<Role> getRole() {
		return user.getUserRoles();
	}

	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}
}
