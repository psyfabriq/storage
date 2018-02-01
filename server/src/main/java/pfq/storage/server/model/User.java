package pfq.storage.server.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Document(collection = "user")
@SuppressWarnings("serial")
public class User implements Serializable, Cloneable {

	@Indexed
	private String id;
	private String name;
	private String login;
	private String foldercode;
	private String email;
	//private 
	@JsonIgnore
	private String passwordHash;
	private Date   dateAdd;
	
	@DBRef(lazy = true)
	private List<Role> userRoles;



	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private User() {
		this.userRoles = new ArrayList<Role>();
	}
	
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFoldercode() {
		return foldercode;
	}


	public Date getDateAdd() {
		return dateAdd;
	}

	public String getLogin() {
		return login;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}
	
	@JsonIgnore
	public String[] getUserRolesArray() {
		List<String> l = new ArrayList<String>();
		for (Role r : userRoles) {
			l.add(r.getName());
		}
		return l.toArray(new String[l.size()]);
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	
    public static Builder newBuilder() {
        return new User().new Builder();
    }
    
    @JsonIgnore
    public Builder getBuilder() {
        return this.new Builder();
    }
	
	public class Builder {
		
	    private Builder() { }
	    public  Builder(User u) { }
	    
		public Builder setId(String id) {
			User.this.id = id;
			return this;
		}
	    
		public Builder setPassword(String password) {
			User.this.passwordHash = new BCryptPasswordEncoder().encode(password);
			return this;
		}
		
		public Builder setEmail(String email) {
			User.this.email = email;
			return this;
		}
		
		public Builder setUserRoles(Role role) {
			User.this.userRoles.add(role);
			return this;
		}
		
		public Builder setLogin(String login) {
			User.this.login = login;
			return this;
		}
		
		public Builder setDateAdd(Date dateAdd) {
			User.this.dateAdd = dateAdd;
			return this;
		}
		
		public Builder setName(String name) {
			User.this.name = name;
			return this;
		}
		
		public Builder setFoldercode(String foldercode) {
			User.this.foldercode = foldercode;
			return this;
		}
		
		
		public User build() {
            return User.this;
        }
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Login :" + this.name);
		sb.append(" ");
		sb.append("Pass :" + this.passwordHash);
		return sb.toString();
	}
    
	
    
	

}
