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
import pfq.store.model.FileItemFX;

public class DashboardController extends Controller implements Initializable  {
	 private  ObjectMapper mapper = new ObjectMapper();
	 private ObservableList<FileItemFX> fileData = FXCollections.observableArrayList();
	 
	 private final FilterableTreeItem<TreeObject> rootNodeTree = new FilterableTreeItem<>(new TreeObject("Root Folder", "/", "/","0"));

	 
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
						//hb.getChildren().addAll(btnOpen, btnEdit, btnDelete);
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
				           
			                //rootNodeTree.getInternalChildren().add(empLeaf);
			                boolean found = false;
			                
			                for (TreeItem<TreeObject> depNode : rootNodeTree.getChildren()) {
				                if (depNode.getValue().getId().contentEquals(objNode.get("id").asText())) {
				                    found = true;
				                    break;
				                }
				                
				  
				            }
			                
			                if (!found) {
				              
				                rootNodeTree.getInternalChildren().add(empLeaf);
				     
				            }
			                /*
				            
				            
				            for (TreeItem<TreeObject> depNode : rootNodeTree.getChildren()) {
				                if (depNode.getValue().contentEquals(objNode.get("path").asText())) {
				                    ((FilterableTreeItem)depNode).getInternalChildren().add(empLeaf);
				                    found = true;
				                    break;
				                }
				            }

				            if (!found) {
				                FilterableTreeItem<TreeObject> depNode = new FilterableTreeItem<>(objNode.get("path").asText());
				                rootNodeTree.getInternalChildren().add(depNode);
				                depNode.getInternalChildren().add(empLeaf);
				            }
				            */
					        		                    
					    }
					}
					
					if (filesNode.isArray()) {
					    for (final JsonNode objNode : filesNode) {
					        System.out.println(objNode);
					    }
					}
				}
			        
/*			        
			        Map<String, Object> map = AppUtil.getValues(AppUtil.convertStreamToString(instream));
			        map = (Map<String, Object>) map.get("Result");
			        
			        for (HashMap<String, Object> folder : (List<HashMap<String, Object>>) map.get("folders")) {
			        	 //System.out.println(folder.toString());
			        	JsonNode myObjects = mapper.readValue(mapper.writeValueAsString(folder), JsonNode.class);
			        	 System.out.println(myObjects);
					}
*/

		           // System.out.println(map.get("folders"));
		           
			}
			//System.out.println(res.get);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public class TreeObject {
		String name;
		String path;
		String parrent;
		String id;

	    public TreeObject(String name, String path, String parrent, String id) {
	        this.name    = name;
	        this.path    = path;
	        this.parrent = parrent;
	        this.id      = id;
	    }
		public String getName() {
			return name;
		}
		public String getPath() {
			return path;
		}
		public String getParrent() {
			return parrent;
		}
		public String getId() {
			return id;
		}
		
		@Override
		public String toString() {
		    return this.name;
		}
	    
	}
	
    @FunctionalInterface
    public interface TreeItemPredicate<T> {

        boolean test(TreeItem<T> parent, T value);

        static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
            return (parent, value) -> predicate.test(value);
        }

    }

    
	 public class FilterableTreeItem<T> extends TreeItem<T> {
	        final private ObservableList<TreeItem<T>> sourceList;
	        private FilteredList<TreeItem<T>> filteredList;
	        private ObjectProperty<TreeItemPredicate<T>> predicate = new SimpleObjectProperty<>();


	        public FilterableTreeItem(T value) {
	            super(value);
	            this.sourceList = FXCollections.observableArrayList();
	            this.filteredList = new FilteredList<>(this.sourceList);
	            this.filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
	                return child -> {
	                    // Set the predicate of child items to force filtering
	                    if (child instanceof FilterableTreeItem) {
	                        FilterableTreeItem<T> filterableChild = (FilterableTreeItem<T>) child;
	                        filterableChild.setPredicate(this.predicate.get());
	                    }
	                    // If there is no predicate, keep this tree item
	                    if (this.predicate.get() == null)
	                        return true;
	                    // If there are children, keep this tree item
	                    if (child.getChildren().size() > 0)
	                        return true;
	                    // Otherwise ask the TreeItemPredicate
	                    return this.predicate.get().test(this, child.getValue());
	                };
	            }, this.predicate));
	            setHiddenFieldChildren(this.filteredList);
	        }

	        protected void setHiddenFieldChildren(ObservableList<TreeItem<T>> list) {
	            try {
	                Field childrenField = TreeItem.class.getDeclaredField("children"); //$NON-NLS-1$
	                childrenField.setAccessible(true);
	                childrenField.set(this, list);

	                Field declaredField = TreeItem.class.getDeclaredField("childrenListener"); //$NON-NLS-1$
	                declaredField.setAccessible(true);
	                list.addListener((ListChangeListener<? super TreeItem<T>>) declaredField.get(this));
	            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	                throw new RuntimeException("Could not set TreeItem.children", e); //$NON-NLS-1$
	            }
	        }

	        public ObservableList<TreeItem<T>> getInternalChildren() {
	            return this.sourceList;
	        }

	        public void setPredicate(TreeItemPredicate<T> predicate) {
	            this.predicate.set(predicate);
	        }

	        public TreeItemPredicate getPredicate() {
	            return predicate.get();
	        }

	        public ObjectProperty<TreeItemPredicate<T>> predicateProperty() {
	            return predicate;
	        }
	    }


}
