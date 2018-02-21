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
import pfq.store.display.Controller;

public class ToolbarController extends Controller implements Initializable {
	private List<CallBackToolbar> listeners = new ArrayList<CallBackToolbar>();
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    	//loadUI("dashboard");
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
			 page = FXMLLoader.load(getClass().getResource("/pfq/store/components/"+ui+".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(CallBackToolbar listener : listeners){
            listener.pageToCallBack(Optional.ofNullable(page));
        }
    }


}
