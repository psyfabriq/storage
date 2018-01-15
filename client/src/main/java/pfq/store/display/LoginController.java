package pfq.store.display;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pfq.store.LoginManager;

public class LoginController {
	
	  @FXML private TextField user;
	  @FXML private TextField password;
	  @FXML private Button loginButton;
	  
	  public void initialize() {}
	  
	  public void initManager(final LoginManager loginManager) {
	    loginButton.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent event) {
	        String sessionID = authorize();
	        if (sessionID != null) {
	          loginManager.authenticated(sessionID);
	        }
	      }
	    });
	  }
	  
	  private String authorize() {
		    return 
		      "open".equals(user.getText()) && "sesame".equals(password.getText()) 
		            ? generateSessionID() 
		            : null;
		  }
	  
	  private static int sessionID = 0;

	  private String generateSessionID() {
	    sessionID++;
	    return "xyzzy - session " + sessionID;
	  }


}
