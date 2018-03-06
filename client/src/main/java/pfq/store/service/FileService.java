package pfq.store.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import pfq.store.AppUtil;
import pfq.store.MemoryUtil;
import pfq.store.components.CallBackPreviewPane;
import pfq.store.components.PreviewPane;

public class FileService implements CallBackPreviewPane {
	
	private static FileService instance;
	
	private  ObservableList<PreviewPane> fileData ;
	private CallBackFileService listenerElements;
	//private ConnettionService   connectionService;
	
	protected Semaphore smp = new Semaphore(1); 
	protected CountDownLatch uploadactive; 
	private  ObjectMapper mapper = new ObjectMapper();
	
	private boolean nowUpload = false;
	

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
						listenerElements.updateListElementsCallBack(Optional.ofNullable(remitem), true);
					}
					for (PreviewPane additem : change.getAddedSubList()) {
						listenerElements.updateListElementsCallBack(Optional.ofNullable(additem), false);
					}

				}
			}
		});
	}
	
	public void addElements(List<File> files){
		for (File file : files) {
			
			boolean exists      =      file.exists();     
			//boolean isDirectory =      file.isDirectory(); 
			boolean isFile      =      file.isFile(); 
			String  name        =      file.getName(); 
			
			System.out.println(exists);
			
			if(exists && isFile) {
		    	PreviewPane f = new PreviewPane(this,file);
		    	FontAwesomeIconView fv = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
		        fv.setSize("20");
		    	f.setButtonIco(fv);
		    	f.setTextLabel(name);
		    	
					//String  extension   =      getFileExtension(name);
					String  mimetype    =      new MimetypesFileTypeMap().getContentType(file);
					//String type         =      mimetype.split("/")[0];

					try {
						if( ImageIO.read(file) != null)
						{
							String localUrl;
							
								localUrl = file.toURI().toURL().toString();
								Image image = new Image(localUrl);
								f.setImage(image);
							
						}
						else 
						{
							System.out.println(mimetype);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
			    	f.animateButtonStart();
			    	fileData.add(f);
			
			}
			
		}
		
	}
	/*
	private static String getFileExtension(String mystr) {
	    int index = mystr.indexOf('.');
	    return index == -1? null : mystr.substring(index);
	}
	*/

	public ObservableList<PreviewPane> getFileData() {
		return fileData;
	}

	@Override
	public void removeElementCallBack(Optional<PreviewPane> pp) {
		if(pp.isPresent())
			fileData.remove(pp.get());	
	}
	
	public void startUpload(String parentpath) {
		uploadactive = new CountDownLatch(fileData.size());	
		Thread myThready = new Thread(new Runnable()
		{
			public void run() 
			{
				try {
					nowUpload = true;
					listenerElements.uploadedStatus(true);
					for (PreviewPane previewPane : fileData) {
						new Thread(new UploadObject(smp, uploadactive, previewPane, parentpath, listenerElements.getContext().connettionService)).start();
					}
					uploadactive.await();
					nowUpload = false;
					listenerElements.uploadedStatus(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		myThready.start();
	}
	


	public boolean getStatusUpload() {
		return nowUpload;
	}
	
	public class UploadObject implements Runnable {
		private   Semaphore smp ; 
		private   PreviewPane pp;
		private   String      parrentpath;
		private   ConnettionService connectionService;
		//CallBackPreviewPane call;
		 
		private UploadObject(Semaphore smp,CountDownLatch uploadactive, PreviewPane pp,String parrentpath,ConnettionService connectionService) {
			this.smp = smp;
			this.pp = pp;
			this.parrentpath = parrentpath;
			this.connectionService = connectionService;
		}

		@Override
		public void run() {
			try {
				smp.acquire(); //Ждем пока другой файл не загрузиться
				HashMap<String,String> variables = new HashMap<>();
				variables.put("parrent", parrentpath);
				variables.put("name", pp.getTextLabel());
				HttpResponse res = connectionService.doPost("/file/api/item-upload", variables, RequestType.FILE, pp.getValueFile());
				HttpEntity entity = res.getEntity();
				if (entity != null) {
				        InputStream instream = entity.getContent();
				        JsonNode rootNode = mapper.readValue(AppUtil.convertStreamToString(instream), JsonNode.class);
				        instream.close();
				        System.out.println(rootNode.toString());
				}
				Thread.sleep(1000);
				
			} catch (InterruptedException | URISyntaxException | IOException e) {
				e.printStackTrace();
			}finally {
				uploadactive.countDown();
				smp.release();
				pp.removeMe();
			}
		} 	 
	 }
}
