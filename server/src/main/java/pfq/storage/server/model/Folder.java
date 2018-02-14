package pfq.storage.server.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pfq.storage.server.model.exception.FileBuilderException;

@Document(collection = "folder")
public class Folder implements Serializable, Cloneable {
	
	@Indexed
	private String   id;
	@JsonIgnore
	private String   userid;
	private String   name;
	private String   path;
	private String   type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date     time;
	@DBRef(lazy = false)
	private Folder   parrent;

	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	



	public String getId() {
		return id;
	}
	
	public String getUserId() {
		return userid;
	}


	public String getName() {
		return name;
	}
	
	public Date getTime() {
		return time;
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}
	
	public Folder getParrent() {
		return parrent;
	}
	

	@JsonIgnore
    public Builder getBuilder() {
        return this.new Builder();
    }
    
    public static Builder newBuilder() {
        return new Folder().new Builder();
    }
    

	public class Builder {
		private Builder() {
			Folder.this.type = "dir";
			Folder.this.time = new Date();
		}
		
	    public Builder setUserId(String userid) {
	    	Folder.this.userid = userid;
		   return this;
	    }
	    
		public Builder setName(String name) {
			Folder.this.name = name;
			return this;
		}

		public Builder setPath(String path) {
			Folder.this.path = path;
			return this;
		}
		
		public Builder setTime(Date time) {
			Folder.this.time = time;
			return this;
		}

		public Builder setParrent(Folder parrent) {
			Folder.this.parrent = parrent;
			return this;
		}

		public Folder build() throws FileBuilderException{
			
			if ("".equals(Folder.this.name)||Folder.this.name == null) {
				throw new FileBuilderException("Name is empty!");
			}
			if ("".equals(Folder.this.userid)||Folder.this.userid == null) {
				throw new FileBuilderException("Not set User ID!");
			}
			if ("".equals(Folder.this.path)||Folder.this.path == null) {
				throw new FileBuilderException("Not set path!");
			}
			
			return Folder.this;
		}

	}
	
	
}
