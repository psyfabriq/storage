package pfq.storage.server.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pfq.storage.server.model.User.Builder;
import pfq.storage.server.model.exception.FileBuilderException;

@Document(collection = "file")
@SuppressWarnings("serial")
public class File implements Serializable, Cloneable,Comparable<File>  {
	
	@Indexed
	private String   id;
	@JsonIgnore
	private String   userid;
	private String   name;
	private String   rights;
	private String   mimeType;
	private long     size;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date     time;
	private String   path;
	private boolean  isImage;
	private String   type;
	@DBRef(lazy = false)
	private Folder   parrent;
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	


	private File() {
		super();	
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


	public String getRights() {
		return rights;
	}


	public long getSize() {
		return size;
	}


	public Date getTime() {
		return time;
	}

	public String getPath() {
		return path;
	}
	
	
	
    public String getMimeType() {
		return mimeType;
	}


	public boolean isImage() {
		return isImage;
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
        return new File().new Builder();
    }


	public class Builder {
    	private Builder() { 
    		  File.this.type = "file";
    		  File.this.time = new Date();
    		}
    	
    	    
		    public Builder setUserId(String userid) {
			   File.this.userid = name;
			   return this;
		    }

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



    		public Builder setPath(String path) {
    			File.this.path = path;
    			return this;
    		}
    		
    		public Builder setParrent(Folder parrent) {
    			File.this.parrent = parrent;
    			return this;
    		}
	
    	
		public File build() throws FileBuilderException {
			
			if ("".equals(File.this.name)||File.this.name == null) {
				throw new FileBuilderException("Name is empty!");
			}
			if ("".equals(File.this.userid)||File.this.userid == null) {
				throw new FileBuilderException("Not set User ID!");
			}
			if ("".equals(File.this.path)||File.this.path == null) {
				throw new FileBuilderException("Not set path!");
			}
			
			
            return File.this;
        }
    }


	@Override
	public int compareTo(File o) {
		return id.compareTo(o.getId());
	}

}
