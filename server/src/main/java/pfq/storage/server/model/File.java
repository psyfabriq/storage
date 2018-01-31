package pfq.storage.server.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pfq.storage.server.model.User.Builder;

@Document(collection = "file")
@SuppressWarnings("serial")
public class File implements Serializable, Cloneable {
	
	@Indexed
	private String   id;
	private String   name;
	private String   rights;
	private long     size;
	private Date     time;
	private TypeFile type;
	private boolean  isimage;
	private String   path;
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getRights() {
		return rights;
	}


	public long getSize() {
		return size;
	}


	public Date getTime() {
		return time;
	}


	public TypeFile getType() {
		return type;
	}


	public boolean isIsimage() {
		return isimage;
	}


	public String getPath() {
		return path;
	}
	
    @JsonIgnore
    public Builder getBuilder() {
        return this.new Builder();
    }
    
    public static Builder newBuilder() {
        return new File().new Builder();
    }


	public class Builder {
    	private Builder() { }
    	


    		public Builder setName(String name) {
    			File.this.name = name;
    			return this;
    		}


    		public Builder setRights(String rights) {
    			File.this.rights = rights;
    			return this;
    		}


    		public Builder setSize(long size) {
    			File.this.size = size;
    			return this;
    		}


    		public Builder setTime(Date time) {
    			File.this.time = time;
    			return this;
    		}


    		public Builder setType(TypeFile type) {
    			File.this.type = type;
    			return this;
    		}


    		public Builder setIsimage(boolean isimage) {
    			File.this.isimage = isimage;
    			return this;
    		}


    		public Builder setPath(String path) {
    			File.this.path = path;
    			return this;
    		}


    	
    	
		public File build() {
            return File.this;
        }
    }

}
