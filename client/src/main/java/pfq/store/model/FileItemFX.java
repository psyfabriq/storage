package pfq.store.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class FileItemFX {
	
	private   SimpleStringProperty  id   = new SimpleStringProperty();
	private   SimpleStringProperty  name = new SimpleStringProperty();
	private   SimpleStringProperty  path = new SimpleStringProperty();
	private   SimpleStringProperty  type = new SimpleStringProperty();
	private   SimpleLongProperty    size = new SimpleLongProperty();
	private   SimpleStringProperty  time = new SimpleStringProperty();
	private                  Image  icon;
	


	public FileItemFX(String id, String name, String path,
			String type, Long size, String time) {
		super();
		this.id.setValue(id);
		this.name.setValue(name);
		this.path.setValue(path);
		this.type.setValue(type);
		this.size.setValue(size);
		this.time.setValue(time);
		
		if("dir".equals(type)) {
			icon = new Image("/pfq/store/img/bookmark-alt-flat/128x128.png");
		}else if ("file".equals(type)) {
			icon = new Image("/pfq/store/img/document-flat/128x128.png");
		}
	}
	

	public String getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getPath() {
		return path.get();
	}

	public String getType() {
		return type.get();
	}

	public Long getSize() {
		return size.get();
	}

	public String getTime() {
		return time.get();
	}


	public Image getIcon() {
		return icon;
	}

  
	
	
	
	
	
	
}
