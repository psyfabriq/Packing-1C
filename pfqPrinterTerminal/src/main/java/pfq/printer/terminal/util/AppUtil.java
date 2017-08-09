package pfq.printer.terminal.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
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
    
    public static Map<String, Object> getValues(String json){
        Map<String, Object> map = new HashMap<String, Object>();
        ResponseStatus rs = ResponseStatus.ERROR;
        try {
            map = mapper.readValue(new ByteArrayInputStream(json.getBytes("UTF-8")), new TypeReference<Map<String, Object>>(){});
            rs = ResponseStatus.OK;
            logger.info(json);
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
    
}