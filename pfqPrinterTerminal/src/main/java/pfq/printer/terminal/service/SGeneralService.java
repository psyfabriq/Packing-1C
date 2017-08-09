package pfq.printer.terminal.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import pfq.printer.terminal.util.PFQloger;


@Service
@Scope(value = "prototype")
public class SGeneralService {
    private Logger logger = PFQloger.getLogger(SGeneralService.class,Level.ALL);
    
    private static final SGeneralService INSTANCE = new SGeneralService();
    
    
    @Autowired private AppService appService;
    
    private Map<String, Object> mapobj ;

    
    private SGeneralService(){ 
    	mapobj = new HashMap<String, Object>();
        logger.debug("Singletone INSTANCE");
    }
    
    public static synchronized  SGeneralService getInstance(){	
           return INSTANCE;
    }
    
    public void addOrder(String key, Object data){
    	if(!mapobj.containsKey(key)){
    		mapobj.put(key, data);
    		System.out.println("Received greeting " + data);
    		appService.getDocumetToPrint(key);
    		
    	}
    	
    }
    
   
    
    

}

