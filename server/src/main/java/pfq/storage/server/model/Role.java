package pfq.storage.server.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@SuppressWarnings("serial")
public class Role implements Serializable,Cloneable {
	
	@Indexed
	String id;
	String name;
	
	  @Override
	    protected Object clone() throws CloneNotSupportedException {
	      return super.clone();
	   } 
}
