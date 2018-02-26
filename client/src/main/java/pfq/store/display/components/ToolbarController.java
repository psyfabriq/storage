package pfq.store.display.components;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import pfq.store.config.ContextStateApp;
import pfq.store.display.Controller;
import pfq.store.display.LoginController;

public class ToolbarController extends Controller implements Initializable {
	private List<CallBackToolbar> listeners = new ArrayList<CallBackToolbar>();
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
	public void initContext(ContextStateApp context) {
		super.initContext(context);
		loadUI("dashboard");
	}

	@FXML
    private void loadCreateFolder(ActionEvent event) {
    	loadUI("createfolder");
    }

    @FXML
    private void loadUploadFile(ActionEvent event) {
    	loadUI("upload");
    }

    @FXML
    private void emptyEvent(ActionEvent event) {
    	//loadUI("dashboard");
    }


    @FXML
    private void loadSettings(ActionEvent event) {
    	loadUI("usersettings");
    }
    
    @FXML
    private void dashboardEvent(ActionEvent event) {
    	loadUI("dashboard");
    }
    
    public void addListener(CallBackToolbar listener) {
        listeners.add(listener);
    }

    private void loadUI(String ui) {
    	Parent page = null;
    	try {			 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/pfq/store/components/" + ui + ".fxml"));
			page = (Parent) loader.load();
			Controller controller = null;
			
			switch (ui) {
			case "createfolder":
			    controller = loader.<CreateFolderController>getController();
				break;
			case "upload":
			    controller = loader.<UploadController>getController();
				break;
			case "usersettings":
				
		    //	 controller = loader.<UploadController>getController();
		    //	controller.initContext(context);

				break;
			case "dashboard":
			    controller = loader.<DashboardController>getController();
				break;
			default:
				break;
			}
			
	    	controller.initContext(context);

	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	for(CallBackToolbar listener : listeners){
            listener.pageToCallBack(Optional.ofNullable(page));
        }
    }


}
