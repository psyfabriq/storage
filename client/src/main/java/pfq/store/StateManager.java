
package pfq.store;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pfq.store.display.LoginController;
import pfq.store.display.MainViewController;

public class StateManager  {
	
	private Scene scene;
	private Stage stage;

	  public StateManager(Scene scene,Stage stage) {
	    this.scene = scene;
	    this.stage = stage;
	    this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  }
		  public void showLoginScreen() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("login.fxml")
		      );
		      scene.setRoot((Parent)loader.load());
		      
		      LoginController controller = 
		        loader.<LoginController>getController();
		      stage.show();
		    } catch (IOException ex) {
		    	System.out.println(ex);
		    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }

		  private void showMainView() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("mainview.fxml")
		      );
		      scene.setRoot((Parent) loader.load());
		      MainViewController controller = 
		        loader.<MainViewController>getController();
		      stage.show();
		    } catch (IOException ex) {
		      Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }
		 
}
