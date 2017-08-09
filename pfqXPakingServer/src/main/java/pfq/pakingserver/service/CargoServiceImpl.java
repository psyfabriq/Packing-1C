package pfq.pakingserver.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.CargoUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.ResponseStatus;
import pfq.pakingserver.dao.CargoDao;
import pfq.pakingserver.dao.NomenclatureDao;
import pfq.pakingserver.dao.PalletDao;
import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

@Service
public class CargoServiceImpl implements CargoService {

    private Logger logger = PFQloger.getLogger(CargoService.class, Level.ALL);

    @Autowired
    CargoDao cargoDao;
    
    @Autowired
    PalletDao palletDao;
    
    @Autowired
    NomenclatureDao nomenclatureDao;
    
   // @Autowired
   // MongoOperations mongoOperation;
    
   
    //@see Add Cargo
    @Override
    public String add(Map<String, Object> map) {
        logger.debug("add");
        boolean dp = true;
        
        String id                = (String) map.get("id");
        String barcode           = (String) map.get("barcode");
        String idordershipment   = (String) map.get("idordershipment");
        String nameordershipment = (String) map.get("nameordershipment"); 
        String idSklad           = (String) map.get("idsklad");
        String nameSklad         = (String) map.get("namesklad");
        String namePersonO       = (String) map.get("nameperson");
        String numberKis         = (String) map.get("numberkis");
        String nameClient        = (String) map.get("nameclient");
        String nameClientP       = map.get("nameclientp")!=null?(String) map.get("nameclientp"):"";
        Date dateOut             = null;
        if(map.get("nameclientp")!=null){
            try {
                Timestamp timeStamp = new Timestamp(Long.parseLong((String) map.get("dateout")));
                 dateOut = new java.sql.Date(timeStamp.getTime());
            } catch (Exception e) {
                logger.error("Coud not set dateOUT");
            }
        }

        if(id == null || id.isEmpty()){dp=false;}
        if(barcode == null || barcode.isEmpty() && dp !=false){dp=false;}
        if(idordershipment == null || idordershipment.isEmpty() && dp !=false){dp=false;}
        if(nameordershipment == null || nameordershipment.isEmpty() && dp !=false){dp=false;}
        if(numberKis == null || numberKis.isEmpty() && dp !=false){dp=false;}
        //if(nameClient == null || nameClient.isEmpty() && dp !=false){dp=false;}
        if(idSklad == null || idSklad.isEmpty() && dp !=false){dp=false;}
        
        if(dp !=false){
            Cargo c = new Cargo();
            c.setBarcode(barcode);
            c.setId(id);
            c.setIdOrderShipment(idordershipment);
            c.setNameOrderShipment(nameordershipment);
            c.setIdSklad(idSklad);
            c.setNameSklad(nameSklad!=null?nameSklad:"");
            c.setNamePersonO(namePersonO!=null?namePersonO:"");
            c.setNumberKis(numberKis);
            c.setNameClient(nameClient);
            c.setNameClientP(nameClientP);
            c.setDateOut(dateOut);
     
            return AppUtil.getResponseJson(cargoDao.addCargo(c));
        }else{return AppUtil.getResponseJson("Not set all parametrs", ResponseStatus.ERROR);}
    }
    //@see Remove Cargo
    @Override
    public String remove(Map<String, Object> map) {
        logger.debug("remove");
        Cargo c = new Cargo();
        c.setBarcode((String) map.get("barcode"));
        c.setId((String) map.get("id"));
   
        return AppUtil.getResponseJson(cargoDao.deleteCargo(c));
    }
    //@see List Cargo
    @Override
    public List<Cargo> listCargo() {
        logger.debug("listCargo");
        return cargoDao.getAllCargo();
    }
    //@see Get Cargo
    @Override
    public String getCargo(Map<String, Object> map) {
        logger.debug("getcargo");
        String result = "";

        Cargo cargo = cargoDao.findCargo((String) map.get("barcode"));
        
        boolean isHtml      =  map.get("html")== null?false:(boolean) map.get("html"); // work only full
        
        if ((boolean) map.get("full")) {
            if(isHtml){
                Map<String, Object> mapo = new HashMap<String, Object>();
                if (cargo != null) {
                    mapo.put("id", cargo.getId());
                    mapo.put("barcode", cargo.getBarcode());
                    mapo.put("passed", cargo.isPassed());
                    mapo.put("dateAdd", cargo.getDateAdd());
                    mapo.put("datePassed", cargo.getDatePassed());
                    mapo.put("dateOUT", cargo.getDateOut());
                    mapo.put("numberKis", cargo.getNumberKis());
                    mapo.put("idSklad", cargo.getIdSklad());
                    mapo.put("nameSklad", cargo.getNameSklad());
                    mapo.put("namePerson", cargo.getNamePersonO());
                    mapo.put("idOrderShipment", cargo.getIdOrderShipment());
                    mapo.put("nameOrderShipment", cargo.getNameOrderShipment());
                    mapo.put("nameClient", cargo.getNameClient());
                    mapo.put("nameClientP", cargo.getNameClientP());
                    
                    mapo.put("nomenclatures", CargoUtil.GenerateHTMLNomenclatures(cargo.getPocketNomenclature(), true));
                    mapo.put("infoCargo", CargoUtil.GenerateHTMLCargo(cargo, true));
                    
                }
                result = cargo != null
                        ? AppUtil.getResponseJson(mapo, ResponseStatus.OK)
                        : AppUtil.getResponseJson("Not found",
                                ResponseStatus.ERROR);
            }else{
                result = cargo != null
                        ? AppUtil.getResponseJson(cargo, ResponseStatus.OK)
                        : AppUtil.getResponseJson("Not found",
                                ResponseStatus.ERROR);
            }
            
        } else {
            if (cargo != null) {
                Map<String, Object> mapo = new HashMap<String, Object>();
                mapo.put("id", cargo.getId());
                mapo.put("barcode", cargo.getBarcode());
                mapo.put("passed", cargo.isPassed());
                mapo.put("dateAdd", cargo.getDateAdd());
                mapo.put("datePassed", cargo.getDatePassed());
                mapo.put("numberKis",cargo.getNumberKis());
                mapo.put("idSklad", cargo.getIdSklad());
                mapo.put("nameSklad", cargo.getNameSklad());
                mapo.put("namePerson", cargo.getNamePersonO());
                mapo.put("idOrderShipment", cargo.getIdOrderShipment());
                mapo.put("nameOrderShipment", cargo.getNameOrderShipment());
                result = AppUtil.getResponseJson(mapo, ResponseStatus.OK);

            } else {
                logger.error("Not found Cargo !!");
                result = AppUtil.getResponseJson("Not found Cargo !!",ResponseStatus.ERROR);
            }
        }
        return result;
    }
    //@see All Cargo
    @Override
    public String getAllCargos(Map<String, Object> map) {
        logger.debug("getallcargos");
        boolean g=true;
        String result = "";
        
       // DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
        
        String idSklad = (String)map.get("Sklad")!=null?(String)map.get("Sklad"):"";
        
        Date dateIsoIn             = null;
             if(map.get("datein")!=null){
                 try {
                    Long dateIsoInL = Long.parseLong((String) map.get("datein"))*1000; 
                     dateIsoIn = new Date(dateIsoInL);
                 } catch (Exception e) {
                     logger.error(e);
                 }
             }
             
             Date dateIsoEnd             = null;
             if(map.get("dateend")!=null){
                 try {
                     Long dateIsoEndL = Long.parseLong((String) map.get("dateend"))*1000; 
                     dateIsoEnd = new Date(dateIsoEndL);
                     
                 } catch (Exception e) {
                     logger.error(e);
                 }
             }

           
             if(dateIsoIn   == null ) {g=false;}
             if(dateIsoEnd  == null ) {g=false;}
             if(idSklad     == null || idSklad.isEmpty())   {g=false;}
             
        if (g) {
            
            Query query = new Query();
            query.addCriteria(Criteria.where("dateAdd").gte(dateIsoIn).lt(dateIsoEnd).and("idSklad").is(idSklad));
               
            List<Cargo> allcargos = cargoDao.findCargoByQueryList(query);
            
            if ((boolean) map.get("full")) {
                result = allcargos.size() != 0
                        ? AppUtil.getResponseJson(allcargos, ResponseStatus.OK)
                        : AppUtil.getResponseJson("No Cargos",
                                ResponseStatus.ERROR);
            } else {
                List<Object> tmpl = new ArrayList<Object>();

                ListIterator<Cargo> listIter = allcargos.listIterator();
                while (listIter.hasNext()) {
                    Cargo tmp = listIter.next();
                    
                        Map<String, Object> mapt = new HashMap<String, Object>();
                        
                        mapt.put("id", tmp.getId());
                        mapt.put("barcode", tmp.getBarcode());
                        mapt.put("passed", CargoUtil.CheckIsPased(tmp));
                        mapt.put("dateAdd", tmp.getDateAdd());
                        mapt.put("datePassed", tmp.getDatePassed());
                        mapt.put("numberKis", tmp.getNumberKis());
                        mapt.put("idSklad", tmp.getIdSklad());
                        mapt.put("nameSklad", tmp.getNameSklad());
                        mapt.put("namePerson", tmp.getNamePersonO());
                        mapt.put("idOrderShipment", tmp.getIdOrderShipment());
                        mapt.put("nameOrderShipment", tmp.getNameOrderShipment());
                        mapt.put("info", tmp.getNameClient());

                        tmpl.add(mapt);
                }

                result = allcargos.size() != 0
                        ? AppUtil.getResponseJson(tmpl, ResponseStatus.OK)
                        : AppUtil.getResponseJson("No Cargos",
                                ResponseStatus.ERROR);
            }

        } else {
            logger.error("Not set datein and dateend !!");
            AppUtil.setError("Not set datein and dateend !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
             
             

        
       

        return result;
    }
    //@see Add Pallet
    @SuppressWarnings("unchecked")
    @Override
    public String addPallet(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo = (String) map.get("barcode");
       
        
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty() ){g=false;}
        
        if(g){
            Cargo cargo = cargoDao.findCargo(ibarcodeCargo);

            Map<String, Object> mapPallet = (Map<String, Object>) map.get("pallet");
            int key       = (int) mapPallet.get("key");
            String barcode= (String) mapPallet.get("barcode");
            String person = (String) mapPallet.get("person");
            
            if(cargo ==null      || barcode ==null || person==null ||
               barcode.isEmpty() || person.isEmpty()){g=false; AppUtil.setError("Not set parammetars pallet !!!");}

            g = g?palletDao.addPallet(key,barcode, person) : false;
            
            cargoDao.editCargo(cargo);
        //g = g?nomenclatureDao.setNomenclatureListMap((List<Map<String, Object>>) mapPallet.get("nomenclatures")):false;
   
        return g?AppUtil.getResponseJson(ResponseStatus.OK):AppUtil.getResponseJson(ResponseStatus.ERROR);
       }else{return AppUtil.getResponseJson("Not set parammetars to found Cargo !",ResponseStatus.ERROR);}
    }
    //@see Remove Pallet
    @SuppressWarnings("unchecked")
    @Override
    public String removePallet(Map<String, Object> map) {
        boolean g = true;
        //String idCargo = (String) map.get("id");
        String ibarcodeCargo = (String) map.get("barcode");

        g = cargoDao.findCargo(ibarcodeCargo) != null ? true :  false;
        if(!g)return AppUtil.getResponseJson("Not found Cargo !!",ResponseStatus.ERROR);
        
        Map<String, Object> mapPallet = (Map<String, Object>) map.get("pallet");
        
        if(palletDao.findPallet((String) mapPallet.get("barcode"))!=null){
            List<Nomenclature> ln = nomenclatureDao.getListNomenclature();
            g = cargoDao.setNomenclatureListTopPocket(ibarcodeCargo, ln);
            if(!g)return AppUtil.getResponseJson("Coud not move nomenclaturs !!",ResponseStatus.ERROR);
           
               g = g?palletDao.deletePallet((String) mapPallet.get("barcode")):false;
               if(!g)return AppUtil.getResponseJson("Coud not delete Pallet !!",ResponseStatus.ERROR);
            
        } else { return AppUtil.getResponseJson("Not found Pallet !!",ResponseStatus.ERROR);}
        
        cargoDao.editCargo();
        
        return AppUtil.getResponseJson(ResponseStatus.OK);
    }
    //@see Get Pallet
    @SuppressWarnings("unchecked")
    @Override
    public String getPallet(Map<String, Object> map) {
        String result = "";
        
        //String idCargo = (String) map.get("id");
        String ibarcodeCargo = (String) map.get("barcode");
        
        boolean isHtml      =  map.get("html")== null?false:(boolean) map.get("html"); // work only full
        
        Cargo cargo = cargoDao.findCargo(ibarcodeCargo);
 
        if (cargo!=null) {
            Map<String, Object> mapPallet = (Map<String, Object>) map.get("pallet");
            Pallet ptmp = palletDao.findPallet((String) mapPallet.get("barcode"));
            if(ptmp!=null){
                if ((boolean) map.get("full")) {
                    result = AppUtil.getResponseJson(ptmp,ResponseStatus.OK);
                }else{
                    Map<String, Object> mapo = new HashMap<String, Object>();
                    mapo.put("key", ptmp.getKey());
                    mapo.put("barcode", ptmp.getBarcode());
                   // mapo.put("passed", ptmp.isPassed());
                    mapo.put("dateAdd", ptmp.getDateAdd());
                   // mapo.put("datePassed", ptmp.getDatePassed());
                    mapo.put("person", ptmp.getNamePersonS());
                    if (isHtml){ 
                       mapo.put("nomenclaturespallet", CargoUtil.GenerateHTMLNomenclatures(ptmp.getNomenclatureWrapper(), true));
                    }else{
                        mapo.put("nomenclaturespallet", ptmp.getNomenclatureWrapper()); 
                    }
                    result = AppUtil.getResponseJson(mapo, ResponseStatus.OK);
                }
            }else{ result = AppUtil.getResponseJson("Not found Pallet",ResponseStatus.ERROR); }
           
        }else{
            result = AppUtil.getResponseJson("Not found Cargo",ResponseStatus.ERROR);
        }
        
        return result;
    }
    //@see Get All Pallet
    @Override
    public String getAllPallets(Map<String, Object> map) {
        String result = "";
        
        String ibarcodeCargo = (String) map.get("barcode");
        
        Cargo cargo = cargoDao.findCargo(ibarcodeCargo);
        
        if (cargo!=null) {
            List<Pallet> listPallet = palletDao.allPalets();
            if ((boolean) map.get("full")) {
                result = AppUtil.getResponseJson(listPallet,ResponseStatus.OK);
            }else{
                List<Map<String,Object>> listPalletTmp = new ArrayList<Map<String,Object>>();  
                for (int i = 0; i < listPallet.size(); i++) {
                    Map<String, Object> mapo = new HashMap<String, Object>();
                    mapo.put("key", listPallet.get(i).getKey());
                    mapo.put("barcode", listPallet.get(i).getBarcode());
                   // mapo.put("passed", listPallet.get(i).isPassed());
                    mapo.put("dateAdd", listPallet.get(i).getDateAdd());
                  //  mapo.put("datePassed", listPallet.get(i).getDatePassed());
                    mapo.put("person", listPallet.get(i).getNamePersonS());
                    listPalletTmp.add(mapo);
                }
                result = AppUtil.getResponseJson(listPalletTmp,ResponseStatus.OK);
            }
        }else{
            result = AppUtil.getResponseJson("Not found Cargo",ResponseStatus.ERROR);
        }
        return result;
    }
    //@see Add Nomenclatures to Pocket Cargo
    @SuppressWarnings("unchecked")
    @Override
    public String addPocketNGroup(Map<String, Object> map) {
        boolean g = true;
       // String idCargo = (String) map.get("id");
        String ibarcodeCargo = (String) map.get("barcode");
        boolean cleanPocketBefore = false;
        try {
             cleanPocketBefore = map.get("clean")!=null?(boolean)map.get("clean"):false;
        } catch (Exception e) {
            logger.error("parameter clean not boolean !!!!");
        }
        //g = cargoDao.findCargo(ibarcodeCargo) != null ? true : cargoDao.findCargoByID(idCargo) != null ? true : false;
        g = cargoDao.findCargo(ibarcodeCargo) != null ? true : false;

        if(!g){logger.error("Not found Cargo !!");
            return AppUtil.getResponseJson("Not found Cargo !!",ResponseStatus.ERROR);}
        
        if(cleanPocketBefore){
            cargoDao.cleanPocket();
            logger.error("is not error cleanPocketBefore ");
        }
        
        List<Map<String,Object>> PocketWrapper = (List<Map<String,Object>>) map.get("pocketn"); 
        if (PocketWrapper.size() != 0) {
            List<Nomenclature> ln = new ArrayList<Nomenclature>();
            
            for (int i = 0; i < PocketWrapper.size(); i++) {
                Nomenclature nn = new Nomenclature();
                Map<String, Object> objNomenclature = (Map<String, Object>)PocketWrapper.get(i);
                
                nn.setArticle((String) objNomenclature.get("article"));
                nn.setName((String) objNomenclature.get("name"));
                
                try {
                    nn.setAccuracy((boolean)objNomenclature.get("accuracy"));
                } catch (Exception e) {
                    logger.error("coud not set Accuracy ");
                    nn.setAccuracy(false);
                }
                
                
                nn.setEX(objNomenclature.get("ex")!=null?(String) objNomenclature.get("ex"):"");
                nn.setVesname(objNomenclature.get("vesname")!=null?(String) objNomenclature.get("vesname"):"");
                nn.setVes(CargoUtil.tryToDouble(objNomenclature.get("ves")));
                nn.setSelectedcol(0.0);
                nn.setMaxcol(CargoUtil.tryToDouble(objNomenclature.get("maxcol"))); 
                nn.setBarcode(CargoUtil.validBarcodes((List<String>)objNomenclature.get("barcodes")));
               
                ln.add(nn);
                
             
            }
            g = cargoDao.setNomenclatureListTopPocket(ibarcodeCargo, ln);
            if(!g){ logger.error("Coud not set nomenclateres to pocket !!");
                return AppUtil.getResponseJson("Coud not set nomenclateres to pocket !!",ResponseStatus.ERROR);}
        }
        cargoDao.editCargo();
        return AppUtil.getResponseJson(ResponseStatus.OK);
    }
    //@see Replace Nomenclature from Pocket Cargo to Pallet
    @Override
    public String setNomenclatureToPallet(Map<String, Object> map) {
        boolean g = true;
        
       // String idCargo            = (String)map.get("id");
        String ibarcodeCargo      = (String)map.get("barcode");
       
        String barcodePallet      = (String)map.get("barcodepallet");
        String barcodeNomeclature = ((String)map.get("barcodenomeclature")).replaceAll(" ", "");
        
        Double col                =  CargoUtil.tryToDouble(map.get("col"));

        
        //logger.debug(map);
        
        //if(idCargo == null || idCargo.isEmpty()){g=false;}
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}
        
        if(barcodePallet == null || barcodePallet.isEmpty() && g !=false){g=false;}
        if(barcodeNomeclature == null || barcodeNomeclature.isEmpty() && g !=false){g=false;}
       // if(col!=0 && g !=false){g=false;}
        
        if(g){
            Cargo cargo = cargoDao.findCargo(ibarcodeCargo); 
            g = cargo != null ? true : false;
            if(!g){logger.error("Not found Cargo !!");
                AppUtil.setError("Not found Cargo !!");
                return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                else{
                    Pallet pallet =palletDao.findPallet(barcodePallet);
                    g = pallet !=null ? true : false;
                    if(!g){logger.error("Not found Pallet !!");
                    AppUtil.setError("Not found Pallet !!");
                    return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                    else{
                        Nomenclature nntmp = cargoDao.giveNomenclatureFromPocket(barcodeNomeclature, col);
                        g = nntmp!=null?true:false;
                        if(!g){logger.error("Not found Nomenclature !!");
                        AppUtil.setError("Not found Nomenclature !!");
                        return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                        else{
                            g = nomenclatureDao.addNomenclature(nntmp, true);
                            if(!g){logger.error("Coud not set Nomenclature to pallet!!");
                            AppUtil.setError("Coud not set Nomenclature to pallet !!");
                            return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                            else{
                                cargoDao.editCargo();
                                Map<String, Object> mapo = new HashMap<String, Object>();
                                mapo.put("nomenclaturespocket", CargoUtil.GenerateHTMLNomenclatures(cargo.getPocketNomenclature(), true));
                                mapo.put("nomenclaturespallet", CargoUtil.GenerateHTMLNomenclatures(pallet.getNomenclatureWrapper(), true));
                                mapo.put("passed",cargo.isPassed());
                                return AppUtil.getResponseJson(mapo, ResponseStatus.OK);
                                }
                        }
                    }
                }
            }else{logger.error("Not set all parrametrs? to set nomenclatere in pallet !!");
            AppUtil.setError("Not set all parrametrs to set nomenclature in pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);}
    }
    //@see Replace Nomenclature from Pallet to Pocket Cargo
    @Override
    public String setNomenclatureToPocketFromPallet(Map<String, Object> map) {
        boolean g = true;
        
      
        String ibarcodeCargo      = (String)map.get("barcode");
       
        String barcodePallet      = (String)map.get("barcodepallet");
        String barcodeNomeclature = ((String)map.get("barcodenomeclature")).replaceAll(" ", "");
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        Double col                =  CargoUtil.tryToDouble(map.get("col"));

        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty() ){g=false;}
        
        if(barcodePallet == null || barcodePallet.isEmpty() && g !=false){g=false;}
        if(barcodeNomeclature == null || barcodeNomeclature.isEmpty() && g !=false){g=false;}
       // if(col!=0 && g !=false){g=false;}
        
        if(g){
            Cargo cargo = cargoDao.findCargo(ibarcodeCargo);
            g = cargo != null ? true : false;
            if(!g){logger.error("Not found Cargo !!");
                AppUtil.setError("Not found Cargo !!");
                return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                else{
                    Pallet pallet = palletDao.findPallet(barcodePallet);
                    g = pallet !=null ? true : false;
                    if(!g){logger.error("Not found Pallet !!");
                    AppUtil.setError("Not found Pallet !!");
                    return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                    else{
                        Nomenclature nntmp = nomenclatureDao.findNomenclature(barcodeNomeclature);
                        
                        g = nntmp!=null?true:false;
                        if(!g){logger.error("Not found Nomenclature !!");
                        AppUtil.setError("Not found Nomenclature !!");
                        return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                    else {
                        try {
                            if (nntmp.getMaxcol() > col) {
                                nntmp.setMaxcol(nntmp.getMaxcol() - col);
                                nntmp.setSelectedcol(nntmp.getMaxcol() - col);
                                
                               
                                Nomenclature cnntmp = nntmp.clone();
                                cnntmp.setMaxcol(col);
                                cnntmp.setSelectedcol(0.0);
                                logger.error(cnntmp.getMaxcol());
                                
                                cargoDao.addNomenclatureToPocket(cnntmp);
                               // mongoOperation.save(cargoDao.findCargo(ibarcodeCargo));
                                
                            } else if (nntmp.getMaxcol().equals(col)) {
                                Nomenclature cnntmp = nntmp.clone();
                                cnntmp.setMaxcol(col);
                                cnntmp.setSelectedcol(0.0);
                                nomenclatureDao.removeNomenclature(nntmp);
                                
                                cargoDao.addNomenclatureToPocket(cnntmp);
                                logger.error(cnntmp.getMaxcol());
                               // mongoOperation.save(cargoDao.findCargo(ibarcodeCargo));
                            } else {
                                logger.error("Max col !!"+ col.toString());
                                AppUtil.setError("Max col !! "+ col.toString());
                                return AppUtil.getResponseJson(ResponseStatus.ERROR);
                            }

                            
                            
                        } catch (CloneNotSupportedException e) {
                            logger.error(e);
                            return AppUtil.getResponseJson(ResponseStatus.ERROR);
                        }
                        
                        
                        cargoDao.editCargo();
                        Map<String, Object> mapo = new HashMap<String, Object>();
                        mapo.put("nomenclaturespocket", CargoUtil.GenerateHTMLNomenclatures(cargo.getPocketNomenclature(), true));
                        mapo.put("nomenclaturespallet", CargoUtil.GenerateHTMLNomenclatures(pallet.getNomenclatureWrapper(), true));
                        mapo.put("passed",cargo.isPassed());
                        return AppUtil.getResponseJson(mapo, ResponseStatus.OK);
                        
                    }
                        
                    }
                    
                }
            
               
           
            //return AppUtil.getResponseJson(ResponseStatus.OK); 
        }else{
            logger.error("Not set all parrametrs? to set nomenclatere in pallet !!");
            AppUtil.setError("Not set all parrametrs to set nomenclature in pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }
    
    @Override
     public String getNomenclatureInfo(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo      = (String)map.get("barcode");
        String barcodeNomeclature = ((String)map.get("barcodenomeclature")).replaceAll(" ", "");
        String barcodePallet      = (String)map.get("barcodepallet");
        
        boolean reverce           = map.get("reverce")!=null?(boolean)map.get("reverce"):false;
        
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}
        if(barcodeNomeclature == null || barcodeNomeclature.isEmpty() && g !=false){g=false;}
        
        if(g){
            g = cargoDao.findCargo(ibarcodeCargo) != null ? true : false;
            if(!g){logger.error("Not found Cargo !!");
            AppUtil.setError("Not found Cargo !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);}
            else{
                if(!reverce){
                    Nomenclature nntmp = cargoDao.findNomenclatureFromPocket(barcodeNomeclature);
                    g = nntmp != null ? true : false;
                    if (!g) {
                        logger.error("Not found Nomenclature !!");
                        AppUtil.setError("Not found Nomenclature !!");
                        return AppUtil.getResponseJson(ResponseStatus.ERROR);
                    } else {
                        return AppUtil.getResponseJson(nntmp,
                                ResponseStatus.OK);
                    }
                }else{
                    Pallet pallet = palletDao.findPallet(barcodePallet);
                    g = pallet !=null ? true : false;
                    if(!g){logger.error("Not found Pallet !!");
                    AppUtil.setError("Not found Pallet !!");
                    return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                    else{
                        Nomenclature nntmp = nomenclatureDao.findNomenclature(barcodeNomeclature);
                        g = nntmp != null ? true : false;
                        if (!g) {
                            logger.error("Not found Nomenclature !!");
                            AppUtil.setError("Not found Nomenclature !!");
                            return AppUtil.getResponseJson(ResponseStatus.ERROR);
                        } else {
                            return AppUtil.getResponseJson(nntmp,
                                    ResponseStatus.OK);
                        }
                    }
                }
            }
        }
        else{
            logger.error("Not set all parrametrs? to set nomenclatere in pallet !!");
            AppUtil.setError("Not set all parrametrs to set nomenclature in pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }
    
    @Override
    public String getNomenclatureInfoAll(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo       = (String)map.get("barcode");
        String abnomeclature       = (String)map.get("abnomeclature");
        
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}
        if(abnomeclature == null || abnomeclature.isEmpty() && g !=false){g=false;}
        if (g) {
            g = cargoDao.findCargo(ibarcodeCargo) != null ? true : false;
            if(!g){logger.error("Not found Cargo !!");
            AppUtil.setError("Not found Cargo !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);}
            else{
              Nomenclature  tmpNomenclature = null;
              Cargo tmpCargo = cargoDao.getCargo();
              
                for (Nomenclature element : tmpCargo.getPocketNomenclature()) {
                    if (element.getArticle().equals(abnomeclature)
                            || element.getBarcode().contains(abnomeclature)) {
                        try {
                            tmpNomenclature = element.clone();
                        } catch (CloneNotSupportedException e) {
                            logger.error("Error Clonable Nomenclature !!!");
                        }
                        break;
                    }
                }
                
                if(tmpNomenclature == null){
                    boolean b = false;
                    for (Pallet elementp : tmpCargo.getPalletWrapper()) {
                        
                        for (Nomenclature element : elementp.getNomenclatureWrapper()) {
                            if (element.getArticle().equals(abnomeclature)
                                    || element.getBarcode().contains(abnomeclature)) {
                                try {
                                    tmpNomenclature = element.clone();
                                } catch (CloneNotSupportedException e) {
                                    logger.error("Error Clonable Nomenclature !!!");
                                }
                                b = true;
                                break;
                            }
                        }
                        if(b){break;}   
                    }
                }
                
                g = tmpNomenclature != null ? true : false;
                
                if(!g){logger.error("Not found Nomenclature !!");
                        AppUtil.setError("Not found Nomenclature !!");
                        return AppUtil.getResponseJson(ResponseStatus.ERROR);}
                else {
                       String result =  CargoUtil.GenerateHTMLNomenclaturesInfo(tmpNomenclature, tmpCargo, true); 
                       Map<String, Object> mapo = new HashMap<String, Object>();
                       mapo.put("nomenclatureinfo", result);
                       return AppUtil.getResponseJson(mapo,ResponseStatus.OK);
                     }
                     
            }
            
        }else{
            logger.error("Not set all parrametrs  !!");
            AppUtil.setError("Not set all parrametrs  !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }
    
    @Override
    public String setNomenclatureToPalletGroup(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo      = (String)map.get("barcode");
        String barcodePallet      = (String)map.get("barcodepallet");
        List<Map<String,Object>> NomeclatresSR = (List<Map<String,Object>>) map.get("nomenclatures"); 
        
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}   
        if(barcodePallet == null || barcodePallet.isEmpty() && g !=false){g=false;}
        if(NomeclatresSR == null || NomeclatresSR.isEmpty() && g !=false){g=false;}
        
        if(g &&  NomeclatresSR.size() != 0){
            
            for (Map<String, Object> mapelement : NomeclatresSR) {
                Map<String, Object> mapo = new HashMap<String, Object>();
                mapo.put("barcode", ibarcodeCargo);
                mapo.put("barcodepallet", barcodePallet);
                mapo.put("barcodenomeclature", (String)mapelement.get("barcodenomeclature"));
                mapo.put("col", CargoUtil.tryToDouble(mapelement.get("col")));
                Map<String, Object> result =  AppUtil.getValues(setNomenclatureToPallet(mapo),false);
                //g = (boolean)result.get("BStatus");
            }
            
            return AppUtil.getResponseJson(ResponseStatus.OK); 
            
        }else{
            logger.error("Not set all parrametrs? to set nomenclatere in pallet !!");
            AppUtil.setError("Not set all parrametrs to set nomenclature in pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
        
    }
    @Override
    public String setNomenclatureToPocketFromPalletGroup(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo      = (String)map.get("barcode");
        String barcodePallet      = (String)map.get("barcodepallet");
        List<Map<String,Object>> NomeclatresSR = (List<Map<String,Object>>) map.get("nomenclatures");
        
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}   
        if(barcodePallet == null || barcodePallet.isEmpty() && g !=false){g=false;}
        if(NomeclatresSR == null || NomeclatresSR.isEmpty() && g !=false){g=false;}
        
        if(g &&  NomeclatresSR.size() != 0){
            
            for (Map<String, Object> mapelement : NomeclatresSR) {
                Map<String, Object> mapo = new HashMap<String, Object>();
                mapo.put("barcode", ibarcodeCargo);
                mapo.put("barcodepallet", barcodePallet);
                mapo.put("barcodenomeclature", (String)mapelement.get("barcodenomeclature"));
                mapo.put("col", CargoUtil.tryToDouble(mapelement.get("col")));
                Map<String, Object> result =  AppUtil.getValues(setNomenclatureToPocketFromPallet(mapo),false);
               // g = (boolean)result.get("BStatus");
            }
            return AppUtil.getResponseJson(ResponseStatus.OK); 
            
        }else{
            logger.error("Not set all parrametrs? to set nomenclatere in pallet !!");
            AppUtil.setError("Not set all parrametrs to set nomenclature in pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
        
    }
    @Override
    public String getPakingInfo(Map<String, Object> map) {
        boolean g = true;
        String barcodePallet      = (String)map.get("barcodepallet");
        
        if(barcodePallet == null || barcodePallet.isEmpty() ){g=false;}
        
        if(g){
            Cargo fCargo = cargoDao.findCargoByQueryOne("{palletwrapper: {$elemMatch:{barcode:'"+barcodePallet+"'}}}");
            if(fCargo ==null){
                logger.error("Not found pallet !!");
                AppUtil.setError("Not found pallet !!");
                return AppUtil.getResponseJson(ResponseStatus.ERROR); 
            }
            Map<String, Object> mapo = new HashMap<String, Object>();
            mapo.put("infoCargo", CargoUtil.GenerateHTMLCargo(fCargo, true));
            for (Pallet item_pollet : fCargo.getPalletWrapper()) {
                if(item_pollet.getBarcode().equals(barcodePallet)){
                    mapo.put("nomenclaturespallet", CargoUtil.GenerateHTMLNomenclatures(item_pollet.getNomenclatureWrapper(), true)); 
                    break;
                }
            }
            
            return AppUtil.getResponseJson(mapo, ResponseStatus.OK);
        }else{
            logger.error("Not set barcode pallet !!");
            AppUtil.setError("Not set barcode pallet !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }
    @Override
    public String getStatusPakingAll(Map<String, Object> map) {
        boolean g = true;
        
      
        //DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
        String idSklad         = (String)map.get("idsklad");
        
        Date dateIsoIn             = null;
        if(map.get("datein")!=null){
            try {
               // Timestamp timeStamp = new Timestamp(Long.parseLong((String) map.get("datein")));
                
               Long dateIsoInL = Long.parseLong((String) map.get("datein"))*1000; 
                dateIsoIn = new Date(dateIsoInL);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        
        Date dateIsoEnd             = null;
        if(map.get("dateend")!=null){
            try {
                //Timestamp timeStamp = new Timestamp(Long.parseLong((String) map.get("dateend")));
                
                Long dateIsoEndL = Long.parseLong((String) map.get("dateend"))*1000; 
                dateIsoEnd = new Date(dateIsoEndL);
                
            } catch (Exception e) {
                logger.error(e);
            }
        }
        
        if(dateIsoIn   == null ) {g=false;}
        if(dateIsoEnd  == null ) {g=false;}
        if(idSklad     == null || idSklad.isEmpty())   {g=false;}
        
        if(g){
         
            
            
            
            
            //List<Cargo> lCargo = cargoDao.findCargoByQueryList("{idSklad : '"+ idSklad +"', dateAdd: '"+ dateIsoIn + "' }");
            
           Query query = new Query();
           query.addCriteria(Criteria.where("dateAdd").gte(dateIsoIn).lt(dateIsoEnd).and("idSklad").is(idSklad));
            
           List<Cargo> lCargo = cargoDao.findCargoByQueryList(query);
          
            List<Map<String, Object>> arrayResult = new ArrayList<Map<String, Object>>();
            
            for (Cargo cargo : lCargo) {
                Map<String, Object> mapo = new HashMap<String, Object>();
                mapo.put("barcode",cargo.getBarcode());
                mapo.put("idordershipment",cargo.getIdOrderShipment());
                mapo.put("passed",CargoUtil.CheckIsPased(cargo));
                mapo.put("datepassed",cargo.getDatePassed());
               
                arrayResult.add(mapo);       
            }
            return AppUtil.getResponseJson(arrayResult,ResponseStatus.OK);
            
            
        }else{
            logger.error("Not set datein and dateend !!");
            AppUtil.setError("Not set datein and dateend !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }
    @Override
    public String getListArticulsSelected(Map<String, Object> map) {
        boolean g = true;
        String ibarcodeCargo      = (String)map.get("barcode");
        if(ibarcodeCargo == null || ibarcodeCargo.isEmpty()){g=false;}
        if(g){
           
            g = cargoDao.findCargo(ibarcodeCargo) != null ? true : false;
            if(!g){logger.error("Not found Cargo !!");
            AppUtil.setError("Not found Cargo !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);}
            else{
                 List<Pallet> listPallet = palletDao.allPalets(); 
                 Map<String,Double> listArticulsAndColTmp = new HashMap<String,Double>(); 
                 
                 for (int i = 0; i < listPallet.size(); i++) {
                     
                     List<Nomenclature> listNomenclatures = listPallet.get(i).getNomenclatureWrapper();
                     
                     for (Iterator iterator = listNomenclatures.iterator(); iterator.hasNext();) {
                        Nomenclature nomenclature = (Nomenclature)iterator.next();
                        if (listArticulsAndColTmp.containsKey(nomenclature.getArticle())) {
                            Double tmpCol = listArticulsAndColTmp.get(nomenclature.getArticle())+ nomenclature.getSelectedcol() ;
                            listArticulsAndColTmp.put(nomenclature.getArticle(),tmpCol);
                        }else{
                            listArticulsAndColTmp.put(nomenclature.getArticle(), nomenclature.getSelectedcol());
                        }
                     }
                 }
                 
                 return AppUtil.getResponseJson(listArticulsAndColTmp,ResponseStatus.OK);
            }
            
        }else{
            logger.error("Not set barcode Cargo !!");
            AppUtil.setError("Not set barcode Cargo !!");
            return AppUtil.getResponseJson(ResponseStatus.ERROR);
        }
    }

    
}
