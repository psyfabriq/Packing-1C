package pfq.pakingserver.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.ResponseStatus;
import pfq.pakingserver.dao.OrderDao;
import pfq.pakingserver.dao.TerminalDao;
import pfq.pakingserver.model.PrinterMessage;
import pfq.pakingserver.model.TerminalPrinter;

@Service
public class PrinterServiceImpl implements PrinterService {
    
    private Logger logger = PFQloger.getLogger(PrinterService.class, Level.ALL);
    
    @Autowired
    private TerminalDao terminalDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private CargoService cargoService;

    @Override
    public String addTerminal(Map<String, Object> map) {
        logger.debug("addTerminal");
        boolean dp = true;
        
        String id                = (String) map.get("id");
        String name              = (String) map.get("name");
        String idSklad           = (String) map.get("idsklad");
        String nameSklad         = (String) map.get("namesklad");
        
        if(id == null        || id.isEmpty()){dp=false;}
        if(name == null      || name.isEmpty() && dp !=false){dp=false;}
        if(idSklad == null   || idSklad.isEmpty() && dp !=false){dp=false;}
        if(nameSklad == null || nameSklad.isEmpty() && dp !=false){dp=false;}
        
        if(dp !=false){
            TerminalPrinter tp = new TerminalPrinter();
            tp.setId(id);
            tp.setIdSklad(idSklad);
            tp.setName(name);
            tp.setNameSklad(nameSklad);

            return AppUtil.getResponseJson(terminalDao.addTerminal(tp));
        }else{return AppUtil.getResponseJson("Not set all parametrs", ResponseStatus.ERROR);}
    }

    @Override
    public String removeTerminal(Map<String, Object> map) {
        logger.debug("removeTerminal");
        boolean dp = true;
        String id                = (String) map.get("id");
        if(id == null        || id.isEmpty()){dp=false;}
        
        if(dp !=false){
            return AppUtil.getResponseJson(terminalDao.deleteTerminal(id));   
        }else{return AppUtil.getResponseJson("Not set all parametrs", ResponseStatus.ERROR);}
    }

    @Override
    public String addOrder(Map<String, Object> map) {
        logger.debug("addOrder");
        boolean dp = true;
        
        String idCargo                = (String) map.get("idcargo");
        String idPallet               = (String) map.get("idpallet");
        String idSklad                = (String) map.get("idsklad");
        String idTerminal             = "";
  
        
        if(idCargo == null   || idCargo.isEmpty()){dp=false;}
        if(idPallet == null  || idPallet.isEmpty() && dp !=false){dp=false;}
        if(idSklad == null   || idSklad.isEmpty() && dp !=false){dp=false;}
        
        if(dp !=false){
            TerminalPrinter t = terminalDao.findTerminal(idSklad);
            dp = t!=null?true:false;
            if(dp !=false)idTerminal = t.getId();
        }
        
        if(dp !=false){
            PrinterMessage p = new PrinterMessage();
            UUID uuid = UUID.randomUUID();
            p.setId(uuid.toString());
            p.setGuid(idTerminal);
            p.setName(idPallet);
            p.setWork("printing");
            
            Map<String,Object> data = new HashMap<String,Object>();
            Map<String,Object> dataPallet = new HashMap<String,Object>();
            
            dataPallet.put("barcode", idPallet);
            data.put("barcode", idCargo);
            data.put("pallet", dataPallet);
            data.put("html",false);
            data.put("full",true);
            
            p.setData(data);
            orderDao.addOrder(p);
            return AppUtil.getResponseJson(ResponseStatus.OK);
            
        }else{return AppUtil.getResponseJson("Not set all parametrs", ResponseStatus.ERROR);}
    }

    @Override
    public String removeOrder(Map<String, Object> map) {
        logger.debug("removeOrder");
        boolean dp = true;
        
        String id = (String) map.get("id");
        
        if(id == null || id.isEmpty()){dp=false;}
        
        if(dp !=false){
            orderDao.deleteOrder(id);  
        }else{return AppUtil.getResponseJson("Not set all parametrs", ResponseStatus.ERROR);}
        return null;
    }

    @Override
    public List<PrinterMessage> getAllOrders() {
        return orderDao.getAllOrder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getPalletToPrint(String idorder) {
        logger.debug("getPalletToPrint");
        boolean dp = true;
        Map<String, Object> result = new HashMap<String, Object>();
        
        if(idorder == null   || idorder.isEmpty()){dp=false;}
        if(dp){
           PrinterMessage order = orderDao.findOrder(idorder);
           if(order==null){dp=false;}
           else{

              Map<String,Object> data  = (HashMap<String, Object>)order.getData();
              data.put("full", true);
              
              Map<String,Object> cargoParams  =  (HashMap<String, Object>)AppUtil.getValues(cargoService.getCargo(data),false).get("Result");
              Map<String,Object> palletParams =  (HashMap<String, Object>)AppUtil.getValues(cargoService.getPallet(data)).get("Result");
              
              int countPallet = ((List<Map<String,Object>>)cargoParams.get("palletWrapper")).size();
              
              result.put("order", cargoParams.get("numberKis"));
              result.put("data", cargoParams.get("dateOUT"));
              result.put("z", cargoParams.get("nameClient"));
              result.put("p", cargoParams.get("nameClientP"));
              result.put("png", palletParams.get("key")+"/"+Integer.toString(countPallet));
              
              List<Map<String,Object>> list = (List<Map<String,Object>>)palletParams.get("nomenclatureWrapper");
              
              result.put("lists", list);
              
              Double mgm = 0.0;
              Double allcount = 0.0;
              
              for (Map<String, Object> map : list) {
                Double count = (Double)map.get("selectedcol");
                Double ves   = (Double)map.get("ves");
                
                mgm = mgm +(count*ves);
                allcount = allcount + count;
                
              }
              
              
              String imageBase64="";
              
                byte[] pngImageData = null;
                try {

                    Barcode128 code128 = new Barcode128();
                    code128.setCodeType(Barcode128.CODE128);
                    code128.setGenerateChecksum(true);
                    code128.setCode((String) palletParams.get("barcode"));
                    code128.setSize(30);
                    code128.setBaseline(-1);
                    

                    java.awt.Image rawImage = code128 .createAwtImage(Color.BLACK, Color.WHITE);
                    BufferedImage outImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    outImage.getGraphics().drawImage(rawImage, 0, 0, null);
                    ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
                    ImageIO.write(outImage, "png", bytesOut);
                    bytesOut.flush();
                    pngImageData = bytesOut.toByteArray();
                    
                  //  byte[] base64byte = Base64.encodeBase64(pngImageData);
                    
                    String base64String=Base64.encode(pngImageData);
                    
                    imageBase64 = "data:image/png;base64,"+base64String; 
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
               result.put("barcode", imageBase64);
               result.put("mgm", mgm);
               result.put("allcount", allcount);
              
               
           }
        }
        result.put("isFind", dp);

        return result;
    }

}
