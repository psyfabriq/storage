package pfq.store.display.components;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
		addButtonToTable();
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

}
