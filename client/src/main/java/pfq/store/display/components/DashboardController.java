package pfq.store.display.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import pfq.store.AppUtil;
import pfq.store.MemoryUtil;
import pfq.store.config.ContextStateApp;
import pfq.store.display.Controller;
import pfq.store.filters.FilterableTreeItem;
import pfq.store.model.FileItemFX;
import pfq.store.model.TreeObject;
import pfq.store.service.RequestType;

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
	    private TableColumn<FileItemFX, String> actionColumn;
	    
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
	
	
    private void actionOpen(FileItemFX data) {
		if("dir".equals(data.getType())) {
			foundTreeViewChild(data.getId());
			MemoryUtil.put("parrent_path", data.getPath());
			getListFolder();
		}
    }
    
    private void actionDelete(FileItemFX data) {
    	removeItem(data.getType(),data.getId());
    }
    
    private void actionDownload(FileItemFX data) {
    	SaveFile(data.getId());
    }
    
    private void actionEdit(FileItemFX data) {
    	
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
                        	//System.out.println(item);
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
        
        Callback<TableColumn<FileItemFX, String>, TableCell<FileItemFX, String>> cellFactoryButton = new Callback<TableColumn<FileItemFX, String>, TableCell<FileItemFX, String>>() {
            @Override
            public TableCell<FileItemFX, String> call(final TableColumn<FileItemFX, String> param) {
                final TableCell<FileItemFX, String> cell = new TableCell<FileItemFX, String>() {
                	
                	
                	private final ToggleGroup group = new ToggleGroup(); 
                	private final ToggleGroup group_f = new ToggleGroup(); 
                	
                	private final HBox hb_folder = new HBox();
                	private final HBox hb_file = new HBox();
                	
                    private final ToggleButton btnOpen        = new ToggleButton("Open");
                    private final ToggleButton btnEdit        = new ToggleButton("Edit");
                    private final ToggleButton btnDelete      = new ToggleButton("Delete");
                    
                    private final ToggleButton btnDownload_f  = new ToggleButton("Download");
                    private final ToggleButton btnEdit_f      = new ToggleButton("Edit");
                    private final ToggleButton btnDelete_f    = new ToggleButton("Delete");
                    
                    {
                    	hb_folder.setAlignment(Pos.CENTER);
                    	hb_file.setAlignment(Pos.CENTER);
                    	

						btnOpen.setOnAction((ActionEvent event) -> {
							FileItemFX data = getTableView().getItems().get(getIndex());
							btnOpen.setSelected(false);
							actionOpen(data);
						});
                    	btnEdit.setOnAction((ActionEvent event) -> {
	                    	FileItemFX data = getTableView().getItems().get(getIndex());
	                    	btnEdit.setSelected(false);
                            actionEdit(data);
                        });
                    	btnDelete.setOnAction((ActionEvent event) -> {
                        	FileItemFX data = getTableView().getItems().get(getIndex());
                        	btnDelete.setSelected(false);
                            actionDelete(data);
                        });
                    	
                    	
						btnDownload_f.setOnAction((ActionEvent event) -> {
							FileItemFX data = getTableView().getItems().get(getIndex());
							btnDownload_f.setSelected(false);
							actionDownload(data);
						});
                    	btnEdit_f.setOnAction((ActionEvent event) -> {
	                    	FileItemFX data = getTableView().getItems().get(getIndex());
	                    	btnEdit_f.setSelected(false);
                            actionEdit(data);
                        });
                    	btnDelete_f.setOnAction((ActionEvent event) -> {
                        	FileItemFX data = getTableView().getItems().get(getIndex());
                        	btnDelete_f.setSelected(false);
                            actionDelete(data);
                        });
                    	
						btnOpen.setToggleGroup(group);
                	    btnEdit.setToggleGroup(group);
                    	btnDelete.setToggleGroup(group);
                    	
                    	
                    	
						btnDownload_f.setToggleGroup(group_f);
                	    btnEdit_f.setToggleGroup(group_f);
                    	btnDelete_f.setToggleGroup(group_f);
                    }
                    
                    {
                    	
            			//hb_file.getChildren().add(btnEdit_f);
						hb_file.getChildren().add(btnDownload_f);
						hb_file.getChildren().add(btnDelete_f);
	
                    	//hb_folder.getChildren().add(btnEdit);
                    	hb_folder.getChildren().add(btnOpen);
						hb_folder.getChildren().add(btnDelete);
						
			
                    	
                    	
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	
                        	if("dir".equals(item)) {
                                setGraphic(hb_folder);
                        	}else {
                                setGraphic(hb_file);
                        	}

                        }
                    }
                };
                return cell;
            }
        };
		
	      PropertyValueFactory<FileItemFX, String> nameProperty = new PropertyValueFactory<FileItemFX, String>("name");
	      PropertyValueFactory<FileItemFX, Long> sizeProperty   = new PropertyValueFactory<FileItemFX, Long>("size");
	      PropertyValueFactory<FileItemFX, String> dateProperty = new PropertyValueFactory<FileItemFX, String>("time");
	      PropertyValueFactory<FileItemFX, Image> iconProperty  = new PropertyValueFactory<FileItemFX, Image>("icon");
	      PropertyValueFactory<FileItemFX, String> typeProperty  = new PropertyValueFactory<FileItemFX, String>("type");
	      
	      imageColumn.setCellFactory(cellFactory);
	      actionColumn.setCellFactory(cellFactoryButton);
	      
	      nameColumn.setCellValueFactory( nameProperty );
	      sizeColumn.setCellValueFactory( sizeProperty );
	      dateColumn.setCellValueFactory( dateProperty );
	      imageColumn.setCellValueFactory( iconProperty );
	      actionColumn.setCellValueFactory( typeProperty );
	      
	      filesView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
	      
	      nameColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // 30% width
	      sizeColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );    // 5% width
	      imageColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // 5% width
	      dateColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );   // 20% width
	      actionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 30% width
	      emptyColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );   // 30% width


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
	
	private void removeItem(String itemtype, String id ) {
		HashMap<String,String> variables = new HashMap<>();
		variables.put("type", itemtype);
		variables.put("id", id);
		HttpResponse res;
		try {
			res = context.connettionService.doPost("/file/api/item-delete", variables, RequestType.JSON);
			HttpEntity entity = res.getEntity();
			if (entity != null) {
				 InputStream instream = entity.getContent();
				    System.out.println(instream);
			        instream.close();
			        
			        FilterableTreeItem<TreeObject>  currentNodeTree =   MemoryUtil.getObj("current_tree_item").isPresent()?(FilterableTreeItem<TreeObject>)MemoryUtil.getObj("current_tree_item").get():rootNodeTree;
			        
			        for (TreeItem<TreeObject> depNode : currentNodeTree.getChildren()) {
		                if (depNode.getValue().getId().contentEquals(id)) {
		                	currentNodeTree.getInternalChildren().remove(depNode);
		                    break;
		                }
		            }    
			        getListFolder();       
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
    private void SaveFile(String ID){
		HashMap<String,String> variables = new HashMap<>();
		variables.put("id", ID);
	
		try {
			HttpResponse res_prop = context.connettionService.doPost("/file/api/item-download-prop", variables, RequestType.JSON);
			HttpEntity entity_prop = res_prop.getEntity();
			if (entity_prop != null) {
				 InputStream instream = entity_prop.getContent();
			     JsonNode rootNode = mapper.readValue(AppUtil.convertStreamToString(instream), JsonNode.class);
			     instream.close();
			     if (rootNode.get("BStatus").asBoolean()) {
			    	 JsonNode resultNode = rootNode.get("Result");
			    	// String fileName = resultNode.get("name").asText()
			    	// System.out.println(resultNode);
			    	 
			    	 FileChooser fileChooser = new FileChooser();
			    	 fileChooser.setTitle("Save file");
			    	 fileChooser.setInitialFileName(resultNode.get("name").asText());
			    	 File savedFile = fileChooser.showSaveDialog(null);
			    	 if (savedFile != null) {
			    		 System.out.println(savedFile);
			    		 HttpResponse res = context.connettionService.doPost("/file/api/item-download", variables, RequestType.JSON);
			 			 HttpEntity entity = res.getEntity();
			 			 if (entity != null) {
			 				InputStream instream_file = entity.getContent();
			 				
			 				 java.nio.file.Files.copy(
			 						instream_file, 
			 						savedFile.toPath(), 
			 					      StandardCopyOption.REPLACE_EXISTING);
			 					 
			 					    IOUtils.closeQuietly(instream_file);
			 				
			 			 }
			    	 }
			    	 
			     }else {
			    	 // Send not found
			     }
			}
			
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}


    }

	private void getListFolder() {
		HashMap<String,String> variables = new HashMap<>();
		variables.put("parrent", MemoryUtil.get("parrent_path", "/",true));
		fileData.clear();
		try {
			HttpResponse res = context.connettionService.doPost("/file/api/get-list-directory", variables, RequestType.JSON);
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
					       // System.out.println(objNode);
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
					        fileData.add(new FileItemFX(objNode.get("id").asText(),
        		                    objNode.get("name").asText(),
        		                    objNode.get("path").asText(),
        		                    objNode.get("type").asText(),
        		                    objNode.get("size").asLong(), 
        		                    objNode.get("time").asText()));
					    }
					}
				}
			  
		           
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
