package pfq.store.display.components;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTreeView;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import pfq.store.MemoryUtil;
import pfq.store.display.Controller;
import pfq.store.filters.FilterableTreeItem;
import pfq.store.filters.PreviewPane;
import pfq.store.model.TreeObject;
import pfq.store.service.CallBackFileService;
import pfq.store.service.FileService;

public class UploadController extends Controller implements Initializable, CallBackFileService {

	private final FilterableTreeItem<TreeObject> rootNodeTree;
	private  FilterableTreeItem<TreeObject>  currentNodeTree;
	private FileService fileService = FileService.getInstance(this);
	private FontAwesomeIconView fv;


	private String path_parrent;
	
  

    @FXML
    private JFXMasonryPane mansoryPane;
	
    @FXML
    private JFXTreeView<TreeObject> treeView;
    
	public UploadController() {
		super();
		this.rootNodeTree = MemoryUtil.getObj("treeFoders").isPresent()?( FilterableTreeItem<TreeObject>)MemoryUtil.getObj("treeFoders").get() : new FilterableTreeItem<>(new TreeObject("Root Folder", "/", "/","0"));
		this.currentNodeTree =   MemoryUtil.getObj("current_tree_item").isPresent()?(FilterableTreeItem<TreeObject>)MemoryUtil.getObj("current_tree_item").get():rootNodeTree;
	}
	
	

    @FXML
    void buttonUploadAction(ActionEvent event) {

    }
    
    @FXML
    void buttonAddFilesAction(ActionEvent event) {
    	//fileService.addElement();

    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTreeViews();
		this.path_parrent = currentNodeTree.getValue().getPath();

		
		
		 mansoryPane.getChildren().addAll(fileService.getFileData());
		 
	
		 mansoryPane.setOnDragOver(new EventHandler<DragEvent>() {

	            @Override
	            public void handle(DragEvent event) {
	                if (event.getGestureSource() != mansoryPane
	                        && event.getDragboard().hasString()) {
	                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	                }
	                event.consume();
	            }
	        });

		 mansoryPane.setOnDragDropped(new EventHandler<DragEvent>() {

	            @Override
	            public void handle(DragEvent event) {
	                Dragboard db = event.getDragboard();
	                boolean success = false;
	                if (db.hasString()) {
	                    //dropped.setText(db.getString());
	                	//System.out.println(db.getString());
	  
	                	fileService.addElements(db.getFiles());
	                    success = true;
	                }
	                event.setDropCompleted(success);

	                event.consume();
	            }
	        });
		// test();
	}
	
	@SuppressWarnings("unchecked")
	private void initTreeViews() {
			rootNodeTree.setExpanded(true);
			treeView.setRoot(rootNodeTree);
			treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
			        @Override
			        public void changed(ObservableValue observable, Object oldValue,
			                Object newValue) {
			            TreeItem<TreeObject> selectedItem = (TreeItem<TreeObject>) newValue;
			            
			        }

			      });
	   }
	
	
	


	    private String getDefaultColor(int i) {
	        String color = "#FFFFFF";
	        switch (i) {
	            case 0:
	                color = "#8F3F7E";
	                break;
	            case 1:
	                color = "#B5305F";
	                break;
	            case 2:
	                color = "#CE584A";
	                break;
	            case 3:
	                color = "#DB8D5C";
	                break;
	            case 4:
	                color = "#DA854E";
	                break;
	            case 5:
	                color = "#E9AB44";
	                break;
	            case 6:
	                color = "#FEE435";
	                break;
	            case 7:
	                color = "#99C286";
	                break;
	            case 8:
	                color = "#01A05E";
	                break;
	            case 9:
	                color = "#4A8895";
	                break;
	            case 10:
	                color = "#16669B";
	                break;
	            case 11:
	                color = "#2F65A5";
	                break;
	            case 12:
	                color = "#4E6A9C";
	                break;
	            default:
	                break;
	        }
	        return color;
	    }



		@Override
		public void updateListElementsCallBack(Optional<PreviewPane> pp, boolean isRemove) {
			if(pp.isPresent()) {
				if(isRemove) {
					mansoryPane.getChildren().remove(pp.get());
				}else {
					mansoryPane.getChildren().add(pp.get());
				}
			}
		}

}
