package pfq.store;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pfq.store.config.ContextStateApp;


public class Main extends Application {
	  public static void main(String[] args) { launch(args); }
	  @Override public void start(Stage stage) throws IOException {
		   Scene scene = new Scene(new StackPane()); 
		   stage.setScene(scene);
		   StateManager stateManager = new StateManager(scene,stage);
      	   ContextStateApp csa = new ContextStateApp(stateManager); 
	       csa.pull(); 
		    
	  }
}
