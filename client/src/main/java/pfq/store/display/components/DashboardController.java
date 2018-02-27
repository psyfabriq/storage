package pfq.store.display.components;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTreeView;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import pfq.store.AppUtil;
import pfq.store.MemoryUtil;
import pfq.store.config.ContextStateApp;
import pfq.store.display.Controller;
import pfq.store.filters.FilterableTreeItem;
import pfq.store.model.FileItemFX;
import pfq.store.model.TreeObject;

public class DashboardController extends Controller implements Initializable  {
	 private  ObjectMapper mapper = new ObjectMapper();
	 private ObservableList<FileItemFX> fileData = FXCollections.observableArrayList();
	 
	 private final FilterableTreeItem<TreeObject> rootNodeTree;

	 
	    @FXML
	    private JFXTreeView<TreeObject> treeView;

	    @FXML
	    private TableView<FileItemFX> filesView;

	    @FXML
	    private TableColumn<FileItemFX, ?> imageColumn;

	    @FXML
	    private TableColumn<FileItemFX, String> nameColumn;

	    @FXML
	    private TableColumn<FileItemFX, Long> sizeColumn;

	    @FXML
	    private TableColumn<FileItemFX, String> dateColumn;
	    

		public DashboardController() {
			super();
			this.rootNodeTree = MemoryUtil.getObj("treeFoders").isPresent()?( FilterableTreeItem<TreeObject>)MemoryUtil.getObj("treeFoders").get() : new FilterableTreeItem<>(new TreeObject("Root Folder", "/", "/","0"));
	}    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	      PropertyValueFactory<FileItemFX, String> nameProperty = new PropertyValueFactory<FileItemFX, String>("name");
	      PropertyValueFactory<FileItemFX, Long> sizeProperty = new PropertyValueFactory<FileItemFX, Long>("size");
	      PropertyValueFactory<FileItemFX, String> dateProperty = new PropertyValueFactory<FileItemFX, String>("time");
	      
	      nameColumn.setCellValueFactory( nameProperty );
	      sizeColumn.setCellValueFactory( sizeProperty );
	      dateColumn.setCellValueFactory( dateProperty );
	      
		filesView.setItems(fileData);
		rootNodeTree.setExpanded(true);
		treeView.setRoot(rootNodeTree);
		
		addButtonToTable();
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
			            //System.out.println("Selected Text : " + selectedItem.getValue().id);
                        MemoryUtil.put("parrent_path", selectedItem.getValue().getPath());
                        getListFolder();
			        }

			      });
	   }
	   private void addButtonToTable() {
	        TableColumn<FileItemFX, Void> colBtn = new TableColumn("");

	        Callback<TableColumn<FileItemFX, Void>, TableCell<FileItemFX, Void>> cellFactory = new Callback<TableColumn<FileItemFX, Void>, TableCell<FileItemFX, Void>>() {
	            @Override
	            public TableCell<FileItemFX, Void> call(final TableColumn<FileItemFX, Void> param) {
	                final TableCell<FileItemFX, Void> cell = new TableCell<FileItemFX, Void>() {
	                	
	                	
	                	private final ToggleGroup group = new ToggleGroup();
	                	private final HBox hb = new HBox();
	                	
	                    private final ToggleButton btnOpen   = new ToggleButton("Open");
	                    private final ToggleButton btnEdit   = new ToggleButton("Edit");
	                    private final ToggleButton btnDelete = new ToggleButton("Delete");
	                    
					{
						hb.setAlignment(Pos.CENTER);
					}
	                    
	                    {
                    	     	hb.getChildren().add(btnEdit);
	                    	    btnEdit.setToggleGroup(group);
		                    	btnEdit.setOnAction((ActionEvent event) -> {
			                    	FileItemFX data = getTableView().getItems().get(getIndex());
		                            System.out.println("selectedData: " + data);
		                        });  		
	                    }
	                    
	                    {
	                    	btnOpen.setToggleGroup(group);
                    		hb.getChildren().add(btnOpen);
	                    	
	                    	btnOpen.setOnAction((ActionEvent event) -> {
	                        	FileItemFX data = getTableView().getItems().get(getIndex());
	                            //System.out.println("selectedData: " + data.getName());
	                            MemoryUtil.put("parrent_path", data.getPath());
	                            getListFolder();
	                        });
	                    }
	                    
	                    {
	                    	btnDelete.setToggleGroup(group);
                    		hb.getChildren().add(btnDelete);
	                    	btnDelete.setOnAction((ActionEvent event) -> {
	                        	FileItemFX data = getTableView().getItems().get(getIndex());
	                            System.out.println("selectedData: " + data);
	                           
	                        });
	                    }
	                    
	                    

	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(hb);
	                        }
	                    }
	                };
	                return cell;
	            }
	        };

	        colBtn.setCellFactory(cellFactory);

	        filesView.getColumns().add(colBtn);

	    }
	
	@Override
	public void initContext(ContextStateApp context) {
		super.initContext(context);
		getListFolder();

	}

	private void getListFolder() {
		HashMap<String,String> variables = new HashMap<>();
		variables.put("parrent", MemoryUtil.get("parrent_path", "/",true));
		fileData.clear();
		try {
			HttpResponse res = context.connettionService.doPost("/file/api/get-list-directory", variables, true);
			HttpEntity entity = res.getEntity();
			if (entity != null) {
			        InputStream instream = entity.getContent();
			        JsonNode rootNode = mapper.readValue(AppUtil.convertStreamToString(instream), JsonNode.class);
			        instream.close();
			        System.out.println(rootNode.toString());
				if (rootNode.get("BStatus").asBoolean()) {
					JsonNode resultNode = rootNode.get("Result");
					JsonNode folderNode = mapper.readTree(resultNode.get("folders").toString());
					JsonNode filesNode  = mapper.readTree(resultNode.get("files").toString());
					
					if (folderNode.isArray()) {
					    for (final JsonNode objNode : folderNode) {
					        System.out.println(objNode);
					        fileData.add(new FileItemFX(objNode.get("id").asText(),
					        		                    objNode.get("name").asText(),
					        		                    objNode.get("path").asText(),
					        		                    objNode.get("type").asText(),
					        		                    objNode.get("size").asLong(), 
					        		                    objNode.get("time").asText()));
					        
				            FilterableTreeItem<TreeObject> empLeaf = new FilterableTreeItem<>(new TreeObject(objNode.get("name").asText(),
				            		                                                                         objNode.get("path").asText(),
				            		                                                                         "",
				            		                                                                         objNode.get("id").asText()));
			                boolean found = false;
			                
			                for (TreeItem<TreeObject> depNode : rootNodeTree.getChildren()) {
				                if (depNode.getValue().getId().contentEquals(objNode.get("id").asText())) {
				                    found = true;
				                    break;
				                }
				            }
			                
			                if (!found) {             
				                rootNodeTree.getInternalChildren().add(empLeaf);
				                MemoryUtil.putObj("treeFoders", rootNodeTree);
				            }
					        		                    
					    }
					}
					
					if (filesNode.isArray()) {
					    for (final JsonNode objNode : filesNode) {
					        System.out.println(objNode);
					    }
					}
				}
			  
		           
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}
	


    
	 


}
