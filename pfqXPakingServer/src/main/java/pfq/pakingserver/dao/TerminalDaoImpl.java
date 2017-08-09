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
import pfq.pakingserver.model.TerminalPrinter;

@Repository
public class TerminalDaoImpl implements TerminalDao {
    
    private Logger logger = PFQloger.getLogger(TerminalDao.class,Level.ALL);
    
    @Autowired
    MongoOperations mongoOperation;

    @Override
    public boolean addTerminal(TerminalPrinter tp) {
        
        Boolean result = false;
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+tp.getId()+"'},{idSklad:'"+tp.getIdSklad()+"'}]}");
        TerminalPrinter tmpl = mongoOperation.findOne(query, TerminalPrinter.class);

        if(tmpl==null){
            mongoOperation.save(tp);
            result =true;
        }else{ 
             result = false;
             AppUtil.setError("Terminal has on server !!!");
            }
        
        return result;
    }

    @Override
    public boolean deleteTerminal(String id) {
        logger.debug("deleteTerminal");
        boolean result = false;
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+id+"'},{idSklad:'"+id+"'}]}");
        TerminalPrinter tmpl = mongoOperation.findOne(query, TerminalPrinter.class);

        if(tmpl!=null){
            mongoOperation.remove(tmpl);
            result =true;
        }else{ 
             result = false;
             AppUtil.setError("Terminal has not found on server !!!");
            }
        
        return result;
    }

    @Override
    public TerminalPrinter findTerminal(String skladId) {
        logger.debug("findTerminal");
        
        BasicQuery query = new BasicQuery("{$or:[{_id:'"+skladId+"'},{idSklad:'"+skladId+"'}]}");
        TerminalPrinter tmpl = mongoOperation.findOne(query, TerminalPrinter.class);
        
        return tmpl;
    }

    @Override
    public List<TerminalPrinter> getAllTerminal() {
        logger.debug("getAllTerminal");
        return mongoOperation.findAll(TerminalPrinter.class);
    }

}
