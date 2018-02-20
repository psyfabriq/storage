package pfq.store.display;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pfq.store.AppSettings;

public class SettingsController extends Controller implements Initializable{
	
	@FXML
	private JFXTextField host;
	
	@FXML
	private JFXTextField port;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		host.setText(AppSettings.get("host"));
		port.setText(AppSettings.get("port"));
	}
	
	@FXML
	private void handleCancelButtonAction(ActionEvent event) {
		context.setState(context.getState("stop"));
		context.pull();
	}
	
	@FXML
	private void handleSaveButtonAction(ActionEvent event) {
		AppSettings.put("host", host.getText());
		AppSettings.put("port", port.getText());
		AppSettings.save();
		context.setState(context.getState("start"));
		context.pull();
	}

}
