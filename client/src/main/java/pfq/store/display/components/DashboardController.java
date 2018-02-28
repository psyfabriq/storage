package pfq.store.display.components;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTreeView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	    private TableColumn<FileItemFX, Image> imageColumn;

	    @FXML
	    private TableColumn<FileItemFX, String> nameColumn;

	    @FXML
	    private TableColumn<FileItemFX, Long> sizeColumn;

	    @FXML
	    private TableColumn<FileItemFX, String> dateColumn;
	    
	    @FXML
	    private TableColumn<FileItemFX, Void> actionColumn;
	    
	    @FXML
	    private TableColumn<FileItemFX, Void> emptyColumn;

	    

		public DashboardController() {
			super();
			this.rootNodeTree = MemoryUtil.getObj("treeFoders").isPresent()?( FilterableTreeItem<TreeObject>)MemoryUtil.getObj("treeFoders").get() : new FilterableTreeItem<>(new TreeObject("Root Folder", "/", "/","0"));
	}    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	      
		filesView.setItems(fileData);
		rootNodeTree.setExpanded(true);
		treeView.setRoot(rootNodeTree);
		
		//addButtonToTable();
		initColums();
		initTreeViews();
	}
	
	private void initColums() {
		
		Callback<TableColumn<FileItemFX, Image>, TableCell<FileItemFX, Image>> cellFactory = new Callback<TableColumn<FileItemFX, Image>, TableCell<FileItemFX, Image>>() {
            public TableCell<FileItemFX, Image> call(TableColumn<FileItemFX, Image> param) {

                final ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);

                    TableCell<FileItemFX, Image> cell = new TableCell<FileItemFX, Image>(){

                        @Override
                        protected void updateItem(Image item, boolean empty) {
                        	System.out.println(item);
                        	if (empty) {
                        		imageView.setImage(null);
	                        } else {
	                        	imageView.setImage(item);
	                        }
                              
                        }
                    };
                    cell.setGraphic(imageView);
                    return cell;
            }
        };
		
        
        Callback<TableColumn<FileItemFX, Void>, TableCell<FileItemFX, Void>> cellFactoryButton = new Callback<TableColumn<FileItemFX, Void>, TableCell<FileItemFX, Void>>() {
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
		                    	btnEdit.setSelected(false);
	                            System.out.println("selectedData: " + data);
	                            
	                        });  		
                    }
                    
                    {
                    	btnOpen.setToggleGroup(group);
                		hb.getChildren().add(btnOpen);
                		
                    	btnOpen.setOnAction((ActionEvent event) -> {
                    	
                        	FileItemFX data = getTableView().getItems().get(getIndex());
                        	btnOpen.setSelected(false);
                        	
                        	foundTreeViewChild(data.getId());
                            MemoryUtil.put("parrent_path", data.getPath());
                            getListFolder();
                            
                        });
                    }
                    
                    {
                    	btnDelete.setToggleGroup(group);
                		hb.getChildren().add(btnDelete);
                		
                    	btnDelete.setOnAction((ActionEvent event) -> {
                        	FileItemFX data = getTableView().getItems().get(getIndex());
                        	btnDelete.setSelected(false);
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
		
	      PropertyValueFactory<FileItemFX, String> nameProperty = new PropertyValueFactory<FileItemFX, String>("name");
	      PropertyValueFactory<FileItemFX, Long> sizeProperty   = new PropertyValueFactory<FileItemFX, Long>("size");
	      PropertyValueFactory<FileItemFX, String> dateProperty = new PropertyValueFactory<FileItemFX, String>("time");
	      PropertyValueFactory<FileItemFX, Image> typeProperty  = new PropertyValueFactory<FileItemFX, Image>("icon");
	      
	      imageColumn.setCellFactory(cellFactory);
	      actionColumn.setCellFactory(cellFactoryButton);
	      
	      nameColumn.setCellValueFactory( nameProperty );
	      sizeColumn.setCellValueFactory( sizeProperty );
	      dateColumn.setCellValueFactory( dateProperty );
	      imageColumn.setCellValueFactory( typeProperty );
	      
	      filesView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
	      
	      nameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 30% width
	      sizeColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // 5% width
	      imageColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // 5% width
	      dateColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
	      actionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 30% width
	      emptyColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 ); // 30% width


	}
	   
	   @SuppressWarnings("unchecked")
	private void initTreeViews() {
			rootNodeTree.setExpanded(true);
			treeView.setRoot(rootNodeTree);
			
			 treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

			        @Override
			        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			            TreeItem<TreeObject> selectedItem = (TreeItem<TreeObject>) newValue;
			            MemoryUtil.putObj("current_tree_item", selectedItem);
                        MemoryUtil.put("parrent_path", selectedItem.getValue().getPath());
                        getListFolder();
			        }

			      });
	   }
	   private void addButtonToTable() {
	        //TableColumn<FileItemFX, Void> colBtn = new TableColumn("");
	        //colBtn.setMaxWidth( 1f * Integer.MAX_VALUE * 30 ); // 30% width
	        
	     

	       // colBtn.setCellFactory(cellFactory);

	       // filesView.getColumns().add(colBtn);

	    }
	
	@Override
	public void initContext(ContextStateApp context) {
		super.initContext(context);
		getListFolder();

	}
	
	private void foundTreeViewChild(String id) {
        FilterableTreeItem<TreeObject>  currentNodeTree =  MemoryUtil.getObj("current_tree_item").isPresent()?(FilterableTreeItem<TreeObject>)MemoryUtil.getObj("current_tree_item").get():rootNodeTree;
        
        for (TreeItem<TreeObject> depNode : currentNodeTree.getChildren()) {
            if (depNode.getValue().getId().contentEquals(id)) {
	            MemoryUtil.putObj("current_tree_item", depNode);
                break;
            }
        }
	}
	
	private void renoveFolder() {
		
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
			                
			                FilterableTreeItem<TreeObject>  currentNodeTree =  MemoryUtil.getObj("current_tree_item").isPresent()?(FilterableTreeItem<TreeObject>)MemoryUtil.getObj("current_tree_item").get():rootNodeTree;
			                
			                for (TreeItem<TreeObject> depNode : currentNodeTree.getChildren()) {
				                if (depNode.getValue().getId().contentEquals(objNode.get("id").asText())) {
				                    found = true;
				                    break;
				                }
				            }
			                
			                if (!found) {             
			                	currentNodeTree.getInternalChildren().add(empLeaf);
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
