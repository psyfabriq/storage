package pfq.storage.server.model;

import org.springframework.security.core.authority.AuthorityUtils;

@SuppressWarnings("serial")
public class CurrentUser extends org.springframework.security.core.userdetails.User{
	private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public String getId() {
        return user.getId();
    }

    public AuthRole getRole() {
        return user.getRole();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
