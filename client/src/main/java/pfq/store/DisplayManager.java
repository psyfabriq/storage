
package pfq.store;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pfq.store.config.ContextStateApp;
import pfq.store.display.Controller;
import pfq.store.display.LoginController;
import pfq.store.display.MainViewController;
import pfq.store.display.SettingsController;

public class DisplayManager extends Controller  {
	
	private Scene scene;
	private Stage stage;
	//private ContextStateApp context;

	  public DisplayManager(Scene scene,Stage stage) {
	    this.scene = scene;
	    this.stage = stage;
	    this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  }
	  /*  
	  public void initContext(ContextStateApp context) {
		  this.context = context;
	  }
	  
	  */
	  public void closeScreen() {
		  stage.close();
	  }
	  
		  public void showLoginScreen() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("login.fxml")
		      );
		      scene.setRoot((Parent)loader.load());
		      
		      LoginController controller = 
		        loader.<LoginController>getController();
		      controller.initContext(context);
		      stage.show();
		    } catch (IOException ex) {
		    	System.out.println(ex);
		    Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }
		  
		  public void showConfigScreen() {
			    try {
			      FXMLLoader loader = new FXMLLoader(
			        getClass().getResource("settings.fxml")
			      );
			      scene.setRoot((Parent)loader.load());
			      
			      SettingsController controller = loader.<SettingsController>getController();
			      controller.initContext(context);
			      stage.show();
			    } catch (IOException ex) {
			    	System.out.println(ex);
			    Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
			    }
			  }

		  public void showMainScreen() {
		    try {
		      FXMLLoader loader = new FXMLLoader(
		        getClass().getResource("mainview.fxml")
		      );
		      scene.setRoot((Parent) loader.load());
		      MainViewController controller = 
		        loader.<MainViewController>getController();
		      //System.out.println(context==null?false:true);
		      controller.initContext(context);
		      stage.show();
		    } catch (IOException ex) {
		      Logger.getLogger(DisplayManager.class.getName()).log(Level.SEVERE, null, ex);
		    }
		  }
		 
}
