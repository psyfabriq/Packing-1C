package pfq.pakingserver.singl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.PrinterMessage;
import pfq.pakingserver.service.PrinterService;

public class SGeneralService {
    private Logger logger = PFQloger.getLogger(SGeneralService.class,Level.ALL);
    
    
    @Autowired private SimpMessagingTemplate template; 
    @Autowired private PrinterService printerService;
    
    private static final SGeneralService INSTANCE = new SGeneralService();
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();
    

    
    private SGeneralService(){ 
        logger.debug("Singletone INSTANCE");
      
        broadcastTimePeriodically();
       // this.cargoWrapper = new CargoWrapper();
    }
    public static SGeneralService getInstance(){
           return INSTANCE;
    }
    
   public void updatePriceAndBroadcast() {
       List<PrinterMessage> stockPrices = printerService.getAllOrders();
       for(PrinterMessage stock : stockPrices) {
         stock.setTime(new Date());
       }
       template.convertAndSend("/topic/price", stockPrices);
   }
   
   private void broadcastTimePeriodically() {
       scheduler.scheduleAtFixedRate(new Runnable() {
         @Override public void run() {
             updatePriceAndBroadcast();
         }
       }, 1000);
     }
    
}
