package pfq.storage.server.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "folder")
public class Folder implements Serializable, Cloneable {
	
	@Indexed
	private String   id;
	private String   name;
	private String   path;
	@DBRef(lazy = true)
	private Folder   parrent;
	
	
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


	public String getPath() {
		return path;
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
		}

		public Builder setName(String name) {
			Folder.this.name = name;
			return this;
		}

		public Builder setPath(String path) {
			Folder.this.path = path;
			return this;
		}

		public Builder setParrent(Folder parrent) {
			Folder.this.parrent = parrent;
			return this;
		}

		public Folder build() {
			return Folder.this;
		}

	}
	
	
}
