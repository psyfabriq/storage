package pfq.store.model;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileItem {
	
	private  final SimpleStringProperty  id;
	private  final SimpleStringProperty  name;
	private  final SimpleStringProperty  path;
	private  final SimpleStringProperty  parrentPath;
	private  final SimpleBooleanProperty directory;
	private  final SimpleBooleanProperty image;
	private  final SimpleLongProperty    size;
	private  final SimpleDateFormat      date;
	


	public FileItem(SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty path,
			SimpleStringProperty parrentPath, SimpleBooleanProperty directory, SimpleBooleanProperty image,
			SimpleLongProperty size, SimpleDateFormat date) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.parrentPath = parrentPath;
		this.directory = directory;
		this.image = image;
		this.size = size;
		this.date = date;
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getParrentPath() {
		return parrentPath;
	}

	public SimpleBooleanProperty getDirectory() {
		return directory;
	}

	public SimpleBooleanProperty getImage() {
		return image;
	}

	public SimpleLongProperty getSize() {
		return size;
	}

	public SimpleDateFormat getDate() {
		return date;
	}

	public SimpleStringProperty getPath() {
		return path;
	}
	
	
	
	
	
	
}
