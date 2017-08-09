package pfq.pakingserver.dao;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.PrinterMessage;



@Repository
public class OrderDaoImpl implements OrderDao {
    
    private Logger logger = PFQloger.getLogger(OrderDao.class,Level.ALL);

    @Autowired
    MongoOperations mongoOperation;

    @Override
    public boolean addOrder(PrinterMessage pm) {
        logger.debug("addOrder");
        
        Boolean result = false;
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+pm.getId()+"'}]}");
        PrinterMessage tmpl = mongoOperation.findOne(query, PrinterMessage.class);

        if(tmpl==null){
            mongoOperation.save(pm);
            result =true;
        }else{ 
             result = false;
             AppUtil.setError("Order has on server !!!");
            }
        return result;
    }

    @Override
    public boolean editOrder(PrinterMessage pm) {
        logger.debug("editOrder");
        
        Boolean result = false;
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+pm.getId()+"'}]}");
        PrinterMessage tmpl = mongoOperation.findOne(query, PrinterMessage.class);

        if(tmpl!=null){
            mongoOperation.save(pm);
            result =true;
        }else{ 
             result = false;
             AppUtil.setError("Order not found !!!");
            }
        return result;
    }

    @Override
    public boolean deleteOrder(String id) {
        logger.debug("deleteOrder");
        
        boolean result = false;
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+id+"'}]}");
        PrinterMessage tmpl = mongoOperation.findOne(query, PrinterMessage.class);

        if(tmpl!=null){
            mongoOperation.remove(tmpl);
            result =true;
        }else{ 
             result = false;
             AppUtil.setError("Order has not found on server !!!");
            }
        
        return result;
    }


    @Override
    public PrinterMessage findOrder(String id) {
        logger.debug("findOrder");
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+id+"'}]}");
        PrinterMessage tmpl = mongoOperation.findOne(query, PrinterMessage.class);
        
        return tmpl;
    }

    @Override
    public List<PrinterMessage> getAllOrder() {
        //logger.debug("getAllOrder");
        return mongoOperation.findAll(PrinterMessage.class);
    }

}
