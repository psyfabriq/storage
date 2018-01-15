package pfq.store.display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pfq.store.LoginManager;

public class MainViewController {
	  
	  @FXML private Button logoutButton;
	  @FXML private Label  sessionLabel;
	  
	  public void initialize() {}
	  
	  public void initSessionID(final LoginManager loginManager, String sessionID) {
	    sessionLabel.setText(sessionID);
	    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent event) {
	        loginManager.logout();
	      }
	    });
	  }

}
