package pfq.storage.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AppUtil {
    
    private static Logger logger = PFQloger.getLogger(AppUtil.class,Level.ALL);
    private static ObjectMapper mapper = new ObjectMapper();
    private static StringBuilder error  = new StringBuilder();
    
    public static Map<String, Object> getValues(String json,Boolean log){
        Map<String, Object> map = new HashMap<String, Object>();
        ResponseStatus rs = ResponseStatus.ERROR;
        try {
            map = mapper.readValue(new ByteArrayInputStream(json.getBytes("UTF-8")), new TypeReference<Map<String, Object>>(){});
            rs = ResponseStatus.OK;
          //  if(log){logger.info(json);}
        } catch (JsonParseException e) {
            rs = ResponseStatus.ERROR;
            logger.error(e);
        } catch (JsonMappingException e) {
            rs = ResponseStatus.ERROR;
            logger.error(e);
        } catch (IOException e) {
            rs = ResponseStatus.ERROR;
            logger.error(e);
        } 
        map.put("ResponseMessage", getResponseMap(rs));
        return map;
    }
    
    public static Map<String, Object> getValues(String json){
        return getValues(json,true);
    }
    
    public static String getResponseJson(Object result, String error,ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        String  json ="";
        map.put("Result", result);
        map.put("Error", error);
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
        try {
             json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }
    

    
    public static String getResponseJson(String error,ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        String  json ="";
        map.put("Error", error);
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
        try {
             json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }
    
 
    
    public static String getResponseJson(ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        String  json ="";
        map.put("Status", status);
        if (!error.toString().isEmpty()){map.put("Error", error.toString()); error.setLength(0);}
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
        try {
             json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }
    
    public static String getResponseJson(Boolean status){
        Map<String, Object> map = new HashMap<String, Object>();
        String  json ="";
        map.put("BStatus", status);
        if (!error.toString().isEmpty()){map.put("Error", error.toString()); error.setLength(0);}
        if(status){
            map.put("Status", ResponseStatus.OK);
        }else{
            map.put("Status", ResponseStatus.ERROR);
        }
        try {
             json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }
    
    public static String getResponseJson(Object result,ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        String  json ="";
        map.put("Result", result);
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
        try {
             json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }
    
    public static Map<String, Object>  wrapperJson(String wrapper ,Object result) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put(wrapper, result);
    	return map;
    }
    
    public static Map<String, Object> getResponseMap(Object result, String error,ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Result", result);
        map.put("Error", error);
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
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
    
    public static Map<String, Object> getResponseMap(Boolean status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("BStatus", status);
        if(status){
            map.put("Status", ResponseStatus.OK);
        }else{
            map.put("Status", ResponseStatus.ERROR);
        }
        
        return map;
    }
    
    public static Map<String, Object> getResponseMap(Object result,ResponseStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Result", result);
        map.put("Status", status);
        if(ResponseStatus.OK.equals(status)){
            map.put("BStatus", true);
        }else{
            map.put("BStatus", false);
        }
       
        return map;
    }
    
    
    
    public static void setError(String errmessage){
        error.append(errmessage);
    }
    
    public static String getSimpleDate() {
        return getSimpleDate(null);
    }

    
    public static String getSimpleDate(Locale locale) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM,
                ((locale == null) ? Locale.getDefault(Category.DISPLAY) : locale));
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
    
    public static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
        
        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
 
            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
 
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }
    
    public static void createFolder(String uploadFolder) {
    
    	if (!Files.exists(Paths.get(uploadFolder))) {
			try {
				Files.createDirectories(Paths.get(uploadFolder));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }
    
    
    private static final String[] charTable = new String[65536];
    static {
        charTable['А'] = "A";
        charTable['Б'] = "B";
        charTable['В'] = "V";
        charTable['Г'] = "G";
        charTable['Д'] = "D";
        charTable['Е'] = "E";
        charTable['Ё'] = "E";
        charTable['Ж'] = "ZH";
        charTable['З'] = "Z";
        charTable['И'] = "I";
        charTable['Й'] = "I";
        charTable['К'] = "K";
        charTable['Л'] = "L";
        charTable['М'] = "M";
        charTable['Н'] = "N";
        charTable['О'] = "O";
        charTable['П'] = "P";
        charTable['Р'] = "R";
        charTable['С'] = "S";
        charTable['Т'] = "T";
        charTable['У'] = "U";
        charTable['Ф'] = "F";
        charTable['Х'] = "H";
        charTable['Ц'] = "C";
        charTable['Ч'] = "CH";
        charTable['Ш'] = "SH";
        charTable['Щ'] = "SH";
        charTable['Ъ'] = "'";
        charTable['Ы'] = "Y";
        charTable['Ь'] = "'";
        charTable['Э'] = "E";
        charTable['Ю'] = "U";
        charTable['Я'] = "YA";

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char) i;
            char lower = new String(new char[] {idx}).toLowerCase().charAt(0);
            if (charTable[i] != null) {
                charTable[lower] = charTable[i].toLowerCase();
            }
        }
    }
    
    public static String toTranslitLat(String text, boolean wd) {
        char charBuffer[] = text.toCharArray();
        StringBuffer sb = new StringBuffer(text.length());
        for (int i = 0; i < charBuffer.length; i++) {
            String replace = charTable[charBuffer[i]];
            if (replace == null) {
                sb.append(charBuffer[i]);
            } else {
                sb.append(replace);
            }
        }
        if(wd) {
        	return sb.toString().replaceAll("\\W", "").toLowerCase();
        }
        return sb.toString().toLowerCase();
    }
    
    
}
