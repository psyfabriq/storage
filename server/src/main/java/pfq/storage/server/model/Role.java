package pfq.storage.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@SuppressWarnings("serial")
public class Role implements Serializable,Cloneable {
	
	@Indexed
	private String id;
	private String name;
	private List<User> users;
	
	public enum Enum {
		ADMIN, USER
	} 
	
	  @Override
	    protected Object clone() throws CloneNotSupportedException {
	      return super.clone();
	   }

    
	public Role() {
		this.users = new ArrayList<User>();
	}


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<User> getUsers() {
		return users;
	}


	public Role setName(String name) {
		this.name = name;
		return this;
	}


	public Role setUsers(User user) {
		this.users.add(user);
		return this;
	} 
	 
	
	  
}
