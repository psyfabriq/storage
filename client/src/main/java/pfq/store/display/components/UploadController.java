package pfq.store.display.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import pfq.store.MemoryUtil;
import pfq.store.display.Controller;
import pfq.store.filters.FilterableTreeItem;
import pfq.store.model.TreeObject;

public class UploadController extends Controller implements Initializable{

	private final FilterableTreeItem<TreeObject> rootNodeTree;
	private  FilterableTreeItem<TreeObject>  currentNodeTree;

	private String path_parrent;
	
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTreeViews();
		this.path_parrent = currentNodeTree.getValue().getPath();
		
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

}
