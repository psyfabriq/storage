package pfq.store.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import pfq.store.filters.PreviewPane;

public class FileService {
	
	private static FileService instance;
	
	private  ObservableList<PreviewPane> fileData ;
	private CallBackFileService listenerElements;

	private FileService() {
		initObjects();
	}
	
	private static class SingletoneHolder{
		private final static FileService instance = new FileService();
	}
	public static FileService getInstance(CallBackFileService listener) {
		SingletoneHolder.instance.addListenerChangeListElements(listener);
		return SingletoneHolder.instance;
	}
	

    private void addListenerChangeListElements(CallBackFileService listener) {
    	this.listenerElements = listener;
    }

	private void initObjects(){
		this.fileData = FXCollections.observableArrayList();
		this.fileData.addListener((ListChangeListener.Change<? extends PreviewPane> change) -> {
			while (change.next()) {
				if (change.wasUpdated()) {
					System.out.println("Update detected");
				} else if (change.wasPermutated()) {

				} else {
					for (PreviewPane remitem : change.getRemoved()) {
						//System.out.println("Remove detected");
						listenerElements.updateListElementsCallBack(Optional.ofNullable(remitem), true);
					}
					for (PreviewPane additem : change.getAddedSubList()) {
						//System.out.println("Add detected");
						listenerElements.updateListElementsCallBack(Optional.ofNullable(additem), false);
					}

				}
			}
		});
	}
	
	public void addElements(List<File> files){
		for (File file : files) {
			
			boolean exists      =      file.exists();     
			boolean isDirectory =      file.isDirectory(); 
			boolean isFile      =      file.isFile(); 
			String  name        =      file.getName(); 
			
			System.out.println(exists);
			
			if(exists) {
		    	PreviewPane f = new PreviewPane();
		    	FontAwesomeIconView fv = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
		        fv.setSize("20");
		    	f.setButtonIco(fv);
		    	f.setTextLabel(name);
		    	
		    
		    	
				if(isDirectory) {
					
				}else if (isFile) {
					String  extension   =      getFileExtension(name);
					String  mimetype    =      new MimetypesFileTypeMap().getContentType(file);
					String type         =      mimetype.split("/")[0];

					if(type.equals("image"))
					{
						String localUrl;
						try {
							localUrl = file.toURI().toURL().toString();
							Image image = new Image(localUrl);
							f.setImage(image);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						
					}
					else 
					{
						System.out.println(type);
					}
			    	f.animateButtonStart();
			    	fileData.add(f);
				}
						
			}
			
		}
		
	}
	
	private static String getFileExtension(String mystr) {
	    int index = mystr.indexOf('.');
	    return index == -1? null : mystr.substring(index);
	}


	public ObservableList<PreviewPane> getFileData() {
		return fileData;
	}
	
	

	
	

}
