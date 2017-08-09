package pfq.printer.terminal.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pfq.printer.terminal.service.AppService;
import pfq.printer.terminal.service.TerminalService;
import pfq.printer.terminal.util.AppUtil;
import pfq.printer.terminal.util.PFQloger;



@RestController
public class ActionController {
	
	    private Logger logger = PFQloger.getLogger(ActionController.class, Level.ALL);
	    
	    private static final HttpHeaders head = new HttpHeaders();
	    private Map<String, Object> map;
	    
	    @Autowired
	    private AppService appService;
	    
	    @Autowired
		 private TerminalService terminalService;
	   
	    @RequestMapping(value = "/properties", method = RequestMethod.GET)
	    public @ResponseBody ResponseEntity<String> getProperties(ModelMap model) {
	        return new ResponseEntity<String>(appService.getProperoties(map), head, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/printers", method = RequestMethod.GET)
	    public @ResponseBody ResponseEntity<String> getInstaledPrinters(ModelMap model) {
	        return new ResponseEntity<String>(appService.getInstaledPrinters(map), head, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/set-properties", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public ResponseEntity<String> setProperties(@RequestBody String json, HttpServletResponse response) {
	    	System.out.println(json);
	    	map = AppUtil.getValues(json);
	        return new ResponseEntity<String>(appService.setProperoties(map), head, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/connet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public void Connect(@RequestBody String json, HttpServletResponse response) {
	    	System.out.println("Connect");
	    	terminalService.start();
	    }
	    
	    
}
