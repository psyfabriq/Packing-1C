package pfq.pakingserver.dao;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.CargoUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

@Repository
public class CargoDaoImpl implements CargoDao {
    
    private Logger logger = PFQloger.getLogger(CargoDao.class,Level.ALL);
    
    private Cargo tmp ;
    
    @Autowired
    PalletDao palletDao;
    
    @Autowired
    MongoOperations mongoOperation;
    
   
    
    @Override
    public boolean addCargo(Cargo cargo) {
        logger.debug("addCargo");
        boolean result = !checkHasCargoByID(cargo.getId())?!checkHasCargo(cargo.getBarcode()):false;
        
        if(result){
            mongoOperation.save(cargo);
           
        }else{ AppUtil.setError("Cargo has on server !!!");}
        
        return result;
    }

    @Override
    public boolean editCargo(Cargo cargo) {
        logger.debug("editCargo");
        boolean result = false;
        
        BasicQuery querycargo = new BasicQuery("{$or:[{_id:'"+cargo.getId()+"'},"
                                                   + "{barcode:'"+cargo.getBarcode()+"'},"
                                                   + "{idOrderShipment:'"+cargo.getIdOrderShipment()+"'}]}");
        
        tmp = mongoOperation.findOne(querycargo, Cargo.class);
        
        if(tmp != null){
            result=true;
            
            tmp.setPassed(CargoUtil.CheckIsPased(tmp));  
            tmp.setDatePassed(cargo.getDatePassed());
            tmp.setPocketNomenclature(cargo.getPocketNomenclature());
            tmp.setPalletWrapper(cargo.getPalletWrapper());
            mongoOperation.save(tmp);
        }
        
        return result;
    }

    @Override
    public boolean deleteCargo(Cargo cargo) {
        logger.debug("deleteCargo 1");
        boolean result = false;
        
        BasicQuery querycargo = new BasicQuery("{$or:[{_id:'"+cargo.getId()+"'},"
                + "{barcode:'"+cargo.getBarcode()+"'},"
                + "{idOrderShipment:'"+cargo.getIdOrderShipment()+"'}]}");
       
        tmp = mongoOperation.findOne(querycargo, Cargo.class);
        if(tmp != null){
           result=true; 
           mongoOperation.remove(tmp);
        }
      
        return result;
    }



    @Override
    public boolean setPassed(String barcode, boolean passed) {
        logger.debug("setPassed");
        boolean result = false;
        
        findCargo(barcode);
        if(tmp !=null){
          result =true;  
          tmp.setPassed(passed);
          mongoOperation.save(tmp);
        }
        
        return result;
    }

    @Override
    public boolean setPassedByID(String ID, boolean passed) {
        logger.debug("setPassedByID");
        boolean result = false;
        
        findCargoByID(ID);
        if(tmp !=null){
          result =true;  
          tmp.setPassed(passed);
          mongoOperation.save(tmp);
        }
        
        return result;
    }

    @Override
    public Cargo findCargo(String barcode) {
        logger.debug("findCargo");
        
        BasicQuery querycargo = new BasicQuery("{$or:[{barcode:'"+barcode+"'}]}");
        tmp = mongoOperation.findOne(querycargo, Cargo.class);
        if (tmp != null) {
            tmp.setPassed(CargoUtil.CheckIsPased(tmp));
            palletDao.setParrent(tmp);
        }
        return tmp;
    }

    @Override
    public Cargo findCargoByID(String ID) {
        logger.debug("findCargoByID");
        
        BasicQuery querycargo = new BasicQuery("{$or:[{_id:'"+ID+"'},{idOrderShipment:'"+ID+"'}]}");
        tmp = mongoOperation.findOne(querycargo, Cargo.class);
        if (tmp != null) {
            palletDao.setParrent(tmp);
        }
        return tmp;
    }
    
    @Override
    public Cargo findCargoByQueryOne(String query) {
        logger.debug("findCargoByQueryOne");
     
            BasicQuery querycargo = new BasicQuery(query);
            tmp = mongoOperation.findOne(querycargo, Cargo.class);
            palletDao.setParrent(tmp);
            return tmp;
    }
    
    @Override
    public List<Cargo> findCargoByQueryList(String query) {
        logger.debug("findCargoByQueryList");
 
            BasicQuery querycargo = new BasicQuery(query);
            List<Cargo> ltmp = mongoOperation.find(querycargo, Cargo.class);
            return ltmp;
     
    }
    
    @Override
    public List<Cargo> findCargoByQueryList(Query query) {
        logger.debug("findCargoByQueryList 2");
        
        List<Cargo> ltmp = mongoOperation.find(query, Cargo.class);
        return ltmp;
    }

    @Override
    public List<Pallet> getListPallet(String barcode) {
        logger.debug("getListPallet");
        return findCargo(barcode).getPalletWrapper();
    }

    @Override
    public List<Pallet> getListPalletByID(String ID) {
        logger.debug("getListPalletByID");
        return findCargoByID(ID).getPalletWrapper();
    }

    @Override
    public List<Cargo> getAllCargo() {
        logger.debug("getAllCargo");
        return mongoOperation.findAll(Cargo.class);
    }

    @Override
    public boolean checkHasCargo(String barcode) {
        logger.debug("checkHasCargo");
        Boolean result = false;
        
        BasicQuery querycargo = new BasicQuery("{$or:[{barcode:'"+barcode+"'}]}");
        Cargo tmpl = mongoOperation.findOne(querycargo, Cargo.class);
        
        result = tmpl!=null?true:false; 
        
        return result;
    }

    @Override
    public boolean checkHasCargoByID(String ID) {
        logger.debug("checkHasCargoByID");
        Boolean result = false;
        
        BasicQuery querycargo = new BasicQuery("{$or:[{_id:'"+ID+"'},{idOrderShipment:'"+ID+"'}]}");
        Cargo tmpl = mongoOperation.findOne(querycargo, Cargo.class);
        
        result = tmpl!=null?true:false; 
        
        return result;
    }

    @Override
    public boolean setNomenclatureListTopPocket(String barcode,List<Nomenclature> ln) {
        logger.debug("setNomenclatureListTopPocket " + barcode);
        
            findCargo(barcode);
            for (int i = 0; i < ln.size(); i++) {
                if(!checkHasNomenclatureInPocket(ln.get(i),tmp.getPocketNomenclature(),true)){
                    ln.get(i).setPassed(false);
                    ln.get(i).setDatePassed(null);
                    tmp.getPocketNomenclature().add(ln.get(i));
                }
                tmp.setPassed(false);
            }
 
        return editCargo(tmp);
    }
    
    @Override
    public boolean checkHasNomenclatureInPocket(Nomenclature nomenclature, List<Nomenclature> listNomeclature , boolean merge) {
        logger.info("checkHasNomenclatureInPocket"); 
        boolean result = false;
        ListIterator<Nomenclature> listIter = listNomeclature.listIterator();
        while (listIter.hasNext()) {
            Nomenclature tmpn = listIter.next();
            
            if(tmpn.getArticle().equals(nomenclature.getArticle())){
                result = true;
                try {
                    tmpn.setMaxcol(tmpn.getMaxcol()+nomenclature.getMaxcol() + nomenclature.getSelectedcol());
                } catch (Exception e) {
                    logger.error(tmpn.getMaxcol());
                    logger.error(nomenclature.getMaxcol());
                    logger.error(nomenclature.getSelectedcol());
                }
                
              //  tmpn.setSelectedcol(tmpn.getSelectedcol()+nomenclature.getSelectedcol());
                tmpn.setSelectedcol(0.0);
                
                if(!tmpn.getBarcode().equals(nomenclature.getBarcode())){
                    List<String> barcodes = nomenclature.getBarcode();
                    for (int i = 0; i < barcodes.size(); i++) {
                        if(!tmpn.getBarcode().contains(barcodes.get(i))){
                            tmpn.getBarcode().add(barcodes.get(i));
                        }
                    }
                }
                listIter.set(tmpn);
                break;
            }
            
        }
        return result;
    }

    @Override
    public Nomenclature giveNomenclatureFromPocket(String barcodenomenclature,Double col) {
        Nomenclature ntmp = null;
        if(barcodenomenclature !=null && !barcodenomenclature.isEmpty() && col !=0){
            ListIterator<Nomenclature> listIter =tmp.getPocketNomenclature().listIterator();
            while (listIter.hasNext()) {
                Nomenclature tmpn = listIter.next();
                tmpn.setBarcode(CargoUtil.validBarcodes(tmpn.getBarcode()));
                if(tmpn.getBarcode().contains(barcodenomenclature)){
                    if(tmpn.getMaxcol() >= col){
                        try {
                            
                            ntmp = tmpn.clone();
                            ntmp.setMaxcol(col);
                            ntmp.setSelectedcol(col);
                            tmpn.setMaxcol(tmpn.getMaxcol()-col);
                            if(tmpn.getMaxcol() == 0){listIter.remove();  
                               if(tmp.getPocketNomenclature().size() == 0){
                                   tmp.setPassed(true);
                                   tmp.setDatePassed(new Date(System.currentTimeMillis()));
                                   }}
                         } catch (CloneNotSupportedException e) {
                            logger.error(e);
                            AppUtil.setError("Nomenclature coud not give");
                            //e.printStackTrace();
                        }
                    }else{AppUtil.setError("Nomenclature coud not give MaxCol < Col !");}
                    
                    break;
                }
            }
        }
        
        return ntmp;
    }
    
    @Override
    public Nomenclature findNomenclatureFromPocket(String barcodenomenclature) {
        Nomenclature ntmp = null;
        if(barcodenomenclature !=null && !barcodenomenclature.isEmpty()){
            ListIterator<Nomenclature> listIter =tmp.getPocketNomenclature().listIterator();
            while (listIter.hasNext()) {
                Nomenclature tmpn = listIter.next();
                tmpn.setBarcode(CargoUtil.validBarcodes(tmpn.getBarcode()));
                if(tmpn.getBarcode().contains(barcodenomenclature)){
                        try {
                            ntmp = tmpn.clone();
                        } catch (CloneNotSupportedException e) {
                            logger.error(e);
                            AppUtil.setError("Nomenclature coud not find");
                        }
                    break;
                }
            }
        }
        
        return ntmp;
    }

    @Override
    public boolean addNomenclatureToPocket(Nomenclature ntp) {
        boolean result=false;
        if(ntp !=null){
           if(ntp.getMaxcol()>0){
               if(!checkHasNomenclatureInPocket(ntp,tmp.getPocketNomenclature(),true)){
                   ntp.setPassed(false);
                   ntp.setDatePassed(null);
                   tmp.getPocketNomenclature().add(ntp);
               }
               tmp.setPassed(false);
               result = true;
           }
        }
        return result;
    }

    @Override
    public boolean editCargo() {
        boolean result=false;
        if(tmp!=null){
          mongoOperation.save(tmp);
        }
        return result;
    }

    @Override
    public boolean cleanPocket() {
        
        boolean result=false;
        if(tmp!=null){
            List<Nomenclature> nl = tmp.getPocketNomenclature();
            nl.clear();
            tmp.setPocketNomenclature(nl);
            editCargo();
        }
        return result;
    }

    @Override
    public Cargo getCargo() {
        return tmp;
    }



  

    
}
