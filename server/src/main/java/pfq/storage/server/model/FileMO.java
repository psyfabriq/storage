package pfq.storage.server.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pfq.storage.server.model.User.Builder;
import pfq.storage.server.model.exception.FileBuilderException;

@Document(collection = "file")
@SuppressWarnings("serial")
public class FileMO implements Serializable, Cloneable,Comparable<FileMO>  {
	
	@Indexed
	private String   id;
	@JsonIgnore
	private String   userid;
	private String   name;
	private String   rights;
	private String   mimeType;
	private double     size;
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
	
	
	private FileMO() {
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

	public double getSize() {
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
	public Update getUpdate() {
		Update update = new Update();
		update.set("name", name);
		update.set("rights", rights);
		update.set("size", size);
		update.set("time", time);
		update.set("path", path);
		update.set("mimeType", mimeType);
		update.set("parrent", parrent);	
		return update;
	}


	
	@JsonIgnore
    public Builder getBuilder() {
        return this.new Builder();
    }
    
    public static Builder newBuilder() {
        return new FileMO().new Builder();
    }


	public class Builder {
    	private Builder() { 
    		  FileMO.this.type = "file";
    		  FileMO.this.time = new Date();
    		}
    	
    	    
		    public Builder setUserId(String userid) {
			   FileMO.this.userid = userid;
			   return this;
		    }

    		public Builder setName(String name) {
    			FileMO.this.name = name;
    			return this;
    		}

    		public Builder setRights(String rights) {
    			FileMO.this.rights = rights;
    			return this;
    		}
    		
    		public Builder setMime(String mime) {
    			FileMO.this.mimeType = mime;
    			return this;
    		}
    		
    		public Builder setIsImage(boolean isImage) {
    			FileMO.this.isImage = isImage;
    			return this;
    		}

    		public Builder setSize(double size) {
    			FileMO.this.size = size;
    			return this;
    		}

    		public Builder setTime(Date time) {
    			FileMO.this.time = time;
    			return this;
    		}

    		public Builder setPath(String path) {
    			FileMO.this.path = path;
    			return this;
    		}
    		
    		public Builder setParrent(Folder parrent) {
    			FileMO.this.parrent = parrent;
    			return this;
    		}
	
    	
		public FileMO build() throws FileBuilderException {
			
			if ("".equals(FileMO.this.name)||FileMO.this.name == null) {
				throw new FileBuilderException("Name is empty!");
			}
			
			if ("".equals(FileMO.this.userid)||FileMO.this.userid == null) {
				throw new FileBuilderException("Not set User ID!");
			}
			
			if ("".equals(FileMO.this.path)||FileMO.this.path == null) {
				throw new FileBuilderException("Not set path!");
			}
				
            return FileMO.this;
        }
    }


	@Override
	public int compareTo(FileMO o) {
		return id.compareTo(o.getId());
	}

}
