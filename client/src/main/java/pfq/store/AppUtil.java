package pfq.store;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pfq.store.display.MainViewController;


public class AppUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    private static StringBuilder error  = new StringBuilder();
    private static final String IMAGE_LOC = "/resources/icon.png";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(IMAGE_LOC));
    }

    public static void loadWindow(URL loc, String title, Stage parentStage) {
        try {
            Parent parent = FXMLLoader.load(loc);
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    public static String formatDateTimeString(Date date)
    {
        return DATE_FORMAT.format(date);
    }
    
    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
    
    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static Map<String, Object> getValues(String json){
        Map<String, Object> map = new HashMap<String, Object>();
       // ResponseStatus rs = ResponseStatus.ERROR;
        try {
            map = mapper.readValue(new ByteArrayInputStream(json.getBytes("UTF-8")), new TypeReference<Map<String, Object>>(){});
            
        } catch (JsonParseException e) {
        //   rs = ResponseStatus.ERROR;
           
        } catch (JsonMappingException e) {
          //  rs = ResponseStatus.ERROR;
            
        } catch (IOException e) {
          //  rs = ResponseStatus.ERROR;
           
        } 
        //map.put("ResponseMessage", getResponseMap(rs));
        return map;
    }
    
    public static Map<String, Object> getResponseMap(ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
        
        return map;
    }
    

}
