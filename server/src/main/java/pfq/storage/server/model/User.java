package pfq.storage.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@SuppressWarnings("serial")
public class User implements Serializable, Cloneable {

	@Indexed
	String id;
	String name;
	String login;
	String foldercode;
	String email;
    String passwordHash;
	Date dateAdd;
	AuthRole role;
	List<Role> userRoles;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public User() {
		super();
		this.userRoles = new ArrayList<Role>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFoldercode() {
		return foldercode;
	}

	public void setFoldercode(String foldercode) {
		this.foldercode = foldercode;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public AuthRole getRole() {
		return role;
	}

	public void setRole(AuthRole role) {
		this.role = role;
	}
	
	

}
