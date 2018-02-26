package pfq.store.display;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pfq.store.DisplayManager;
import pfq.store.config.ContextStateApp;
import pfq.store.display.components.CallBackToolbar;
import pfq.store.display.components.ToolbarController;

public class MainViewController extends Controller  implements Initializable,CallBackToolbar {
	
	    @FXML
	    private JFXDrawer drawer;
	
	    @FXML
	    private JFXHamburger hamburger;
	    
	    @FXML
	    private StackPane pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	
	
	@Override
	public void initContext(ContextStateApp context) {
		super.initContext(context);

		initDrawer();
		//initPane();
		//System.out.println(context!=null?true:false);
	}


    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pfq/store/components/toolbar.fxml"));
            
        	Parent toolbar = (Parent)loader.load();
        	ToolbarController controller = loader.<ToolbarController>getController();
        	controller.addListener(this);
        	controller.initContext(context);
            drawer.setSidePane(toolbar);
        } catch (IOException ex) {
        	System.out.println(ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isHidden()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });
    }
    

	@Override
	public void pageToCallBack(Optional<Parent> p) {
		if(p.isPresent()) {
			pane.getChildren().clear();
			pane.getChildren().add(p.get());
		}
	}
	
	

}
