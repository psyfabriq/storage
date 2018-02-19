package pfq.store.display;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pfq.store.StateManager;
import pfq.store.config.ContextStateApp;

public class LoginController implements Initializable{

	@FXML
	private JFXTextField username;
	@FXML
	private JFXPasswordField password;
	@FXML
	private Button loginButton;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		//context.setState(context.getState("stop"));
		//context.pull();
	}

	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();
		
		if (!pword.isEmpty() && !uname.isEmpty()) {
			//context.setState(context.getState("main"));
			//context.pull();
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
		
	}



}
