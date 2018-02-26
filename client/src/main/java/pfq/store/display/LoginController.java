package pfq.store.display;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pfq.store.service.ConnettionService;
import pfq.store.service.ConnettionServiceException;

public class LoginController extends Controller implements Initializable{

	@FXML
	private JFXTextField username;
	@FXML
	private JFXPasswordField password;
	@FXML
	private Button loginButton;

	

    public LoginController() {
		super();

	}


	@Override
    public void initialize(URL url, ResourceBundle rb) {
		username.setText("Admin");
		password.setText("Admin");
    }


	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		context.setState(context.getState("stop"));
		context.pull();
	}
	
	@FXML
	private void handleConfigButtonAction(ActionEvent event) {
		context.setState(context.getState("conf"));
		context.pull();
	}


	@FXML
	private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();
		
		if (!pword.isEmpty() && !uname.isEmpty()) {
			
			try {
				context.connettionService = ConnettionService.newBuilder().connect(uname, pword).build();
				context.setState(context.getState("main"));
				context.pull();
			} catch (ConnettionServiceException e) {
	            username.getStyleClass().add("wrong-credentials");
	            password.getStyleClass().add("wrong-credentials");
				//e.printStackTrace();
			}
			

			
        } else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
		
	}



}
