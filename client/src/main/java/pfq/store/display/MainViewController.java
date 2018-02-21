package pfq.store.display;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pfq.store.DisplayManager;
import pfq.store.display.components.CallBackToolbar;
import pfq.store.display.components.ToolbarController;

public class MainViewController extends Controller  implements Initializable,CallBackToolbar {
	
	    @FXML
	    private JFXDrawer drawer;
	
	    @FXML
	    private JFXHamburger hamburger;
	    
	    @FXML
	    private BorderPane pane;
	  

	  
	  public void initSessionID(final DisplayManager loginManager, String sessionID) {
		 /* 
	    sessionLabel.setText(sessionID);
	    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent event) {
	        loginManager.logout();
	      }
	      
	    });
	    */
	  }



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initDrawer();
		initPane();
	}
	private void initPane() {
		try {
			Parent page = FXMLLoader.load(getClass().getResource("/pfq/store/components/dashboard.fxml"));
			pane.setCenter(page);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pfq/store/components/toolbar.fxml"));
            
        	Parent toolbar = (Parent)loader.load();
        	ToolbarController controller = loader.<ToolbarController>getController();
        	controller.addListener(this);
            drawer.setSidePane(toolbar);
        } catch (IOException ex) {
        	System.out.println(ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isHidden()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });
    }
    

	@Override
	public void pageToCallBack(Optional<Parent> p) {
		if(p.isPresent()) {
			pane.setCenter(p.get());
		}
	}
	
	

}
