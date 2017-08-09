package pfq.pakingserver.config;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.service.CargoService;
import pfq.pakingserver.singl.SGeneralService;

@Configuration
public class MongoConfig {
    private Logger logger = PFQloger.getLogger(CargoService.class, Level.ALL);
    @Autowired
    SGeneralService SGS;
    
   // public Boolean isAuth = false;

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
      // MongoCredential mongoCredential = MongoCredential
       //         .createMongoCRCredential("xpaking", "xpaking",
        //                "1234".toCharArray());
      
       // MongoClient mongo = new MongoClient(new ServerAddress("127.0.0.1",27017),Arrays.asList(mongoCredential));
       
        MongoClient mongo = new MongoClient("127.0.0.1");
      //  UserCredentials credentials = new UserCredentials("xpaking","abc123");
     //   SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongo, "xpaking" , credentials);
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongo, "xpaking");
        logger.info("mongoDbFactory");
        return simpleMongoDbFactory;
    }
    
    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        logger.info("mongoTemplate");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        // show error, should off on production to speed up performance
        mongoTemplate.setWriteConcern(WriteConcern.SAFE);
       // try {
       //     SGS.setListCargo(mongoTemplate.findAll(Cargo.class));
       // } catch (Exception e) {
       //     logger.error("OPS");
      //  }
        
        return mongoTemplate;

    }

}
