package pfq.store.display.components;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.controlsfx.control.Notifications;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.util.Duration;
import pfq.store.AppUtil;
import pfq.store.MemoryUtil;
import pfq.store.Notification;
import pfq.store.display.Controller;
import pfq.store.filters.FilterableTreeItem;
import pfq.store.model.TreeObject;


public class CreateFolderController extends Controller implements Initializable {
	
	private final FilterableTreeItem<TreeObject> rootNodeTree;
	private  ObjectMapper mapper = new ObjectMapper();
	   
	
    @FXML
    private JFXTreeView<TreeObject> treeView;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField fpath;

	 

	public CreateFolderController() {
			super();
			this.rootNodeTree = MemoryUtil.getObj("treeFoders").isPresent()?( FilterableTreeItem<TreeObject>)MemoryUtil.getObj("treeFoders").get() : new FilterableTreeItem<>(new TreeObject("Root Folder", "/", "/","0"));
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTreeViews();
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
			            fpath.setText(selectedItem.getValue().getPath());
			        }

			      });
	   }
	   
	    @FXML
	    void handleAddButtonAction(ActionEvent event) {
			HashMap<String,String> variables = new HashMap<>();
			variables.put("name", fname.getText());
			variables.put("parrent", fpath.getText());
			try {
				HttpResponse res = context.connettionService.doPost("/file/api/add-folder", variables, true);
				HttpEntity entity = res.getEntity();
				if (entity != null) {
					 InputStream instream = entity.getContent();
					 JsonNode rootNode    = mapper.readValue(AppUtil.convertStreamToString(instream), JsonNode.class);
					 instream.close();
					 System.out.println(rootNode.toString());
					 if(rootNode.get("BStatus").asBoolean()) {
						 Notification.success("Folder "+fname.getText()+" added");
	                                                         
					 }else {
						 Notification.error("Folder "+fname.getText()+" added error");
					 }

					 
				}
				
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}

	    }
}
