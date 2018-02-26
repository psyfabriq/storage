package pfq.storage.server.model;

import java.util.List;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getUserRolesArray()));
		//System.out.println(toString());
		this.user = user;
	}

	public String getId() {
		return user.getId();
	}

	public List<Role> getRole() {
		return user.getUserRoles();
	}
	
	public String getName() {
		return user.getName();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getWorkFolder() {
		return user.getFoldercode();
	}
	
	public boolean checkHasRole(String rolename) {
		List<Role> r = user.getUserRoles();
		for (Role role : r) {
			if(role.getName().equals(rolename)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}
}
