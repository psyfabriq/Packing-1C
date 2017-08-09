package pfq.pakingserver.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pfq.pakingserver.PFQloger;
import pfq.pakingserver.singl.SGeneralService;

@Controller
public class PrinterTerminalController {
    private Logger logger = PFQloger.getLogger(PrinterTerminalController.class, Level.ALL);
    
    @Autowired private SGeneralService sGeneralService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
      return "main";
    }

}
