package pfq.store;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pfq.store.display.LoginController;
import pfq.store.display.MainViewController;

public class StateManager {
	
	private Scene scene;

	  public StateManager(Scene scene) {
	    this.scene = scene;
	    this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  }

	  public void authenticated(String sessionID) {
		    showMainView(sessionID);
		  }
	  
	  public void logout() {
		    showLoginScreen();
		  }
		  
		  public void showLoginScreen() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("login.fxml")
		      );
		      scene.setRoot((Parent)loader.load());
		      
		      LoginController controller = 
		        loader.<LoginController>getController();
		      controller.initManager(this);
		    } catch (IOException ex) {
		    	System.out.println(ex);
		    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }

		  private void showMainView(String sessionID) {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("mainview.fxml")
		      );
		      scene.setRoot((Parent) loader.load());
		      MainViewController controller = 
		        loader.<MainViewController>getController();
		      controller.initSessionID(this, sessionID);
		    } catch (IOException ex) {
		      Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }
}
