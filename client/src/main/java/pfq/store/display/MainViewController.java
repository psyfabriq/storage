package pfq.store.display;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.VBox;
import pfq.store.StateManager;

public class MainViewController  implements Initializable {
	
	    @FXML
	    private JFXDrawer drawer;
	    @FXML
	    private JFXHamburger hamburger;
	  

	  
	  public void initSessionID(final StateManager loginManager, String sessionID) {
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
	}
	
    private void initDrawer() {
        try {
        	Parent toolbar = FXMLLoader.load(getClass().getResource("/pfq/store/toolbar.fxml"));
            drawer.setSidePane(toolbar);
        } catch (IOException ex) {
        	System.out.println(ex);
           // Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
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
	
	

}
