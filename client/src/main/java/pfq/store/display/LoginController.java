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

public class LoginController implements Initializable{

	@FXML
	private JFXTextField username;
	@FXML
	private JFXPasswordField password;
	@FXML
	private Button loginButton;

	private StateManager stateManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
	public void initManager(final StateManager loginManager) {
		this.stateManager = loginManager;
	}

	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();
		
		if (!pword.isEmpty() && !uname.isEmpty()) {
			stateManager.authenticated("");
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
		
	}



}
