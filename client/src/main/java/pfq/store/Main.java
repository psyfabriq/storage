package pfq.store;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	  public static void main(String[] args) { launch(args); }
	  @Override public void start(Stage stage) throws IOException {
	    Scene scene = new Scene(new StackPane());
	    
	    StateManager loginManager = new StateManager(scene);
	    loginManager.showLoginScreen();

	    stage.setScene(scene);
	    stage.show();
	  }
}
