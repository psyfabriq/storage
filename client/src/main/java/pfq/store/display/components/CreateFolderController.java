package pfq.store.display.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pfq.store.display.Controller;

public class CreateFolderController extends Controller implements Initializable {

	@FXML
	private JFXTextField fname;

	@FXML
	private JFXTextField fpath;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	void handleCancelButtonAction(ActionEvent event) {

	}

	@FXML
	void handleSaveButtonAction(ActionEvent event) {

	}

}
