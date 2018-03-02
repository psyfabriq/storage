package pfq.store.components;

import static javafx.animation.Interpolator.EASE_BOTH;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import pfq.store.service.CallBackFileService;

public class PreviewPane extends AnchorPane   {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private StackPane bodyNode;

    @FXML
    private ImageView imageNode;

    @FXML
    private StackPane bottomNode;

    @FXML
    private Label labelNode;

    @FXML
    private JFXButton buttonNode;
    
    double  widthRoot;
    double  heightRoot; 
    private String bodyColor; 
    private int indexElement  = 1 ; 
    private Timeline animation;
    private CallBackPreviewPane listenerService;
    private File value;
    
    
    public PreviewPane(CallBackPreviewPane listenerService,File value) {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pfq/store/components/imageframe.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initComponents();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.listenerService =  listenerService;
        this.value           =  value;
	}
    
    private void initComponents() {
    	widthRoot = 100;
    	heightRoot = 85;
    	
    	rootNode.setMinWidth(widthRoot);
    	rootNode.setMaxWidth(widthRoot);
    	rootNode.setPrefWidth(widthRoot);
    	rootNode.setMinHeight(heightRoot);
    	rootNode.setMaxHeight(heightRoot);
    	rootNode.setPrefHeight(heightRoot);
        JFXDepthManager.setDepth(rootNode, 1);
        
        
        labelNode.setWrapText(true);
        labelNode.setTextAlignment(TextAlignment.LEFT);
    	
        bodyColor = getDefaultColor((int)(Math.random() * 12) % 12);
        
        bodyNode.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + bodyColor);
        VBox.setVgrow(bodyNode, Priority.ALWAYS);
        
        bottomNode.setStyle("-fx-background-radius: 0 0 5 5; -fx-background-color: rgb(255,255,255,0.87);");
    	
    	SVGGlyph glyph = new SVGGlyph(-1,
                "test",
                "M1008 6.286q18.857 13.714 15.429 36.571l-146.286 877.714q-2.857 16.571-18.286 25.714-8 4.571-17.714 4.571-6.286 "
                + "0-13.714-2.857l-258.857-105.714-138.286 168.571q-10.286 13.143-28 13.143-7.429 "
                + "0-12.571-2.286-10.857-4-17.429-13.429t-6.571-20.857v-199.429l493.714-605.143-610.857 "
                + "528.571-225.714-92.571q-21.143-8-22.857-31.429-1.143-22.857 18.286-33.714l950.857-548.571q8.571-5.143 18.286-5.143"
                + " 11.429 0 20.571 6.286z",
                Color.WHITE);
    	
    	buttonNode.setButtonType(ButtonType.RAISED);
    	buttonNode.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
    	buttonNode.setPrefSize(40, 40);
    	buttonNode.setRipplerFill(Color.valueOf(bodyColor));
    	buttonNode.setScaleX(0);
    	buttonNode.setScaleY(0);
        
        glyph.setSize(20, 20);
        buttonNode.setGraphic(glyph);
        /*
        buttonNode.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
            return bodyNode.getBoundsInParent().getHeight() - buttonNode.getHeight() / 2;
        }, bodyNode.boundsInParentProperty(), buttonNode.heightProperty()));
        */
        StackPane.setMargin(buttonNode, new Insets(0, 12, 0, 0));
        StackPane.setAlignment(buttonNode, Pos.TOP_RIGHT);
        
        imageNode.setImage(new Image("/pfq/store/img/bookmark-alt-flat/256x256.png"));

         animation = new Timeline(new KeyFrame(Duration.millis(240),
                                                       new KeyValue(buttonNode.scaleXProperty(),
                                                                    1,
                                                                    EASE_BOTH),
                                                       new KeyValue(buttonNode.scaleYProperty(),
                                                                    1,
                                                                    EASE_BOTH)));
       // animation.setDelay(Duration.millis(100 * indexElement + 1000));
       // animation.play();
    	
    }

    public void setTextLabel(String text) {
 	   labelNode.setText(text);
    }
    
    public void setBodyColor(String colorcode) {
 	   bodyColor = colorcode;
 	   bodyNode.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + bodyColor);
    }
    
    public void setBodyColor(int colorindex) {
 	   bodyColor = getDefaultColor(colorindex);
 	   bodyNode.setStyle("-fx-background-radius: 5 5 0 0; -fx-background-color: " + bodyColor);
    }
    
    public void animateButtonStart() {
 	   animation.setDelay(Duration.millis(100 * indexElement + 1000));
 	   animation.play();
    }
    
    public void setImage(Image im) {
 	   imageNode.setImage(im);
    }
    
    public void setWidthElement(double width) {   
    	widthRoot = width;
    	rootNode.setMinWidth(widthRoot);
    	rootNode.setMaxWidth(widthRoot);
    	rootNode.setPrefWidth(widthRoot);
 	
    }
    public void setHeightElement(double height) {
 	   	heightRoot = height;
 	   	rootNode.setMinHeight(heightRoot);
     	rootNode.setMaxHeight(heightRoot);
     	rootNode.setPrefHeight(heightRoot);
    }
    
    public void setButtonColor(String colorcode) {
 		buttonNode.setStyle("-fx-background-radius: 40;-fx-background-color: " + colorcode);
 		buttonNode.setRipplerFill(Color.valueOf(bodyColor));
    }
    
    public void setButtonColor(int colorindex) {
 		buttonNode.setStyle("-fx-background-radius: 40;-fx-background-color: " + getDefaultColor(colorindex));
 		buttonNode.setRipplerFill(Color.valueOf(bodyColor));
    }
    
    public void setButtonIco(Node node) {
 	   buttonNode.setGraphic(node);
    }
    
    public void setElementIndex(int index) {
 	   indexElement = index; 
    }
    
    public String getTextLabel() {
    	return labelNode.getText();
    }
    
	public File getValueFile() {
		return value;
	}

	@FXML
    void buttonClick(ActionEvent event) {
		listenerService.removeElementCallBack(Optional.ofNullable(this));
    }
	
	
	  private String getDefaultColor(int i) {
	        String color = "#FFFFFF";
	        switch (i) {
	            case 0:
	                color = "#8F3F7E";
	                break;
	            case 1:
	                color = "#B5305F";
	                break;
	            case 2:
	                color = "#CE584A";
	                break;
	            case 3:
	                color = "#DB8D5C";
	                break;
	            case 4:
	                color = "#DA854E";
	                break;
	            case 5:
	                color = "#E9AB44";
	                break;
	            case 6:
	                color = "#FEE435";
	                break;
	            case 7:
	                color = "#99C286";
	                break;
	            case 8:
	                color = "#01A05E";
	                break;
	            case 9:
	                color = "#4A8895";
	                break;
	            case 10:
	                color = "#16669B";
	                break;
	            case 11:
	                color = "#2F65A5";
	                break;
	            case 12:
	                color = "#4E6A9C";
	                break;
	            default:
	                break;
	        }
	        return color;
	    }


}
