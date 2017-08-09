package pfq.pakingserver;



import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

public class CargoUtil {
    private static Logger logger = PFQloger.getLogger(AppUtil.class,Level.ALL);
    
    public static List <String> GenerateHTMLNomenclatures(List<Nomenclature> lNomenclatures,Boolean toBase64){
       
       List <String> arrL = new ArrayList<String>();
       
       Iterator<Nomenclature> iterator = lNomenclatures.iterator();
        
        while (iterator.hasNext()) {
            Nomenclature nomenclature = (Nomenclature)iterator.next();
            
            String result="";
            
            StringBuilder html = new StringBuilder();
            html.append("<HTML XMLNS:MSIE>");
            html.append("<HEAD>");
            html.append("<META http-equiv=Content-Type content=\"text/html; charset=utf-8\">");
            html.append("<STYLE type=text/css>");
            html.append("P { LINE-HEIGHT: 0px }");
            html.append("DIV.CaptionText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:10pt; font-style:normal; color:#0000ff; font-weight:normal; }");
            html.append("DIV{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#008000; font-weight:normal; }");
            html.append("DIV.ErrorText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#FF0000; font-weight:normal; }");
            html.append("DIV.TipText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:9pt; font-style:normal; color:#ff0000; font-weight:normal; }");
            html.append("</STYLE>");
            html.append("<META content=\"MSHTML 6.00.6001.18183\" name=GENERATOR</HEAD>");
            html.append("<BODY>");
           

                    html.append("<DIV>-------------------------------------------</DIV>");
                    html.append("<DIV class=\"CaptionText\"> Article: "+nomenclature.getArticle()+"</DIV>");
                    html.append("<DIV class=\"CaptionText\">"+nomenclature.getName()+"</DIV>");
                    if(nomenclature.isAccuracy()){
                       html.append("<DIV class=\"CaptionText\"> Кол-во "+nomenclature.getMaxcol()+" ш</DIV>");
                    }else{
                        html.append("<DIV class=\"CaptionText\"> Кол-во "+nomenclature.getMaxcol().intValue()+" ш</DIV>"); 
                    }
                    
                    List<String> listBarcodes=nomenclature.getBarcode();
                    String barcodes = "";
                    for (String barcode : listBarcodes) {
                        barcodes = barcodes+barcode+"; ";
                    }
                    html.append("<DIV>ШК: "+barcodes+"</DIV>");
           
            
            
            html.append("<DIV>-------------------------------------------</DIV>");
            html.append("</BODY>");
            html.append("</HTML>");
            
            if(toBase64){
                Base64.Encoder encoder = Base64.getEncoder();
                result = encoder.encodeToString(html.toString().getBytes(StandardCharsets.UTF_8) );
               
            }else{
                result = html.toString();
            }
            arrL.add(result);
        }
        
        return arrL;
    }
    
    public static String GenerateHTMLCargo(Cargo cargo,Boolean toBase64){
        String result="";
        StringBuilder html = new StringBuilder();
        html.append("<HTML XMLNS:MSIE>");
        html.append("<HEAD>");
        html.append("<META http-equiv=Content-Type content=\"text/html; charset=utf-8\">");
        html.append("<STYLE type=text/css>");
        html.append("P { LINE-HEIGHT: 0px }");
        html.append("DIV.CaptionText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:10pt; font-style:normal; color:#0000ff; font-weight:normal; }");
        html.append("DIV{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#008000; font-weight:normal; }");
        html.append("DIV.ErrorText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#FF0000; font-weight:normal; }");
        html.append("DIV.TipText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:9pt; font-style:normal; color:#ff0000; font-weight:normal; }");
        html.append("</STYLE>");
        html.append("<META content=\"MSHTML 6.00.6001.18183\" name=GENERATOR</HEAD>");
        html.append("<BODY>");
        html.append("<DIV>-------------------------------------------</DIV>");
        
        html.append("<DIV class=\"CaptionText\"> "+cargo.getNameOrderShipment()+"</DIV>");
        html.append("<DIV class=\"CaptionText\"> КИС : "+cargo.getNumberKis()+"</DIV>");
        html.append("<DIV>-------------------------------------------</DIV>");
        html.append("<DIV class=\"CaptionText\"> Коментарий :"+cargo.getNameClient()+"</DIV>");
        html.append("<DIV>-------------------------------------------</DIV>");
        html.append("<DIV class=\"CaptionText\"> "+cargo.getNameSklad()+"</DIV>");
        html.append("<DIV>-------------------------------------------</DIV>");
        html.append("<DIV class=\"CaptionText\" > Собирал : "+cargo.getNamePersonO()+"</DIV>");
        html.append("<DIV>-------------------------------------------</DIV>");
        html.append("</BODY>");
        html.append("</HTML>");
        
        if(toBase64){
            Base64.Encoder encoder = Base64.getEncoder();
            result = encoder.encodeToString(html.toString().getBytes(StandardCharsets.UTF_8) );
           
        }else{
            result = html.toString();
        }
        return result;
    }
    
    public static String GenerateHTMLNomenclaturesInfo(Nomenclature nomenclature,Cargo cargo ,Boolean toBase64){
        
        String result="";
        StringBuilder html = new StringBuilder();
        html.append("<HTML XMLNS:MSIE>");
        html.append("<HEAD>");
        html.append("<META http-equiv=Content-Type content=\"text/html; charset=utf-8\">");
        html.append("<STYLE type=text/css>");
        html.append("P { LINE-HEIGHT: 0px }");
        html.append("DIV.CaptionText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:10pt; font-style:normal; color:#0000ff; font-weight:normal; }");
        html.append("DIV{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#008000; font-weight:normal; }");
        html.append("DIV.ErrorText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:8pt; font-style:normal; color:#FF0000; font-weight:normal; }");
        html.append("DIV.TipText{ LINE-HEIGHT: 20px; font-family:Arial Black; font-size:9pt; font-style:normal; color:#ff0000; font-weight:normal; }");
        html.append("</STYLE>");
        html.append("<META content=\"MSHTML 6.00.6001.18183\" name=GENERATOR</HEAD>");
        html.append("<BODY>");
        
        html.append("<DIV>-------------------------------------------</DIV>");
        html.append("<DIV class=\"CaptionText\"> Артикул : "+nomenclature.getArticle()+"</DIV>");
        html.append("<DIV class=\"CaptionText\"> "+nomenclature.getName()+"</DIV>"); 
        List<String> listBarcodes=nomenclature.getBarcode();
        String barcodes = "";
        for (String barcode : listBarcodes) {
            barcodes = barcodes+barcode+"; ";
        }
        html.append("<DIV>ШК: "+barcodes+"</DIV>");    
        html.append("<DIV>-------------------------------------------</DIV>");
        
        for (Nomenclature element : cargo.getPocketNomenclature()) {
            if(element.getArticle().equals(nomenclature.getArticle())){
                html.append("<DIV class=\"CaptionText\"> Корман </DIV>"); 
                html.append("<DIV>Кол-во: "+element.getMaxcol()+"</DIV>");
                html.append("<DIV>-------------------------------------------</DIV>");
                break;
            }
        }
        for (Pallet elementp : cargo.getPalletWrapper()) {
            
            for (Nomenclature element : elementp.getNomenclatureWrapper()) {
                if(element.getArticle().equals(nomenclature.getArticle())){
                    html.append("<DIV class=\"CaptionText\"> "+elementp.getBarcode()+" </DIV>"); 
                    html.append("<DIV>Кол-во: "+element.getMaxcol()+"</DIV>");
                    html.append("<DIV>-------------------------------------------</DIV>");
                    break;
                }
            }
        }
      
        html.append("</BODY>");
        html.append("</HTML>");
        
        if(toBase64){
            Base64.Encoder encoder = Base64.getEncoder();
            result = encoder.encodeToString(html.toString().getBytes(StandardCharsets.UTF_8) );
           
        }else{
            result = html.toString();
        }
        return result;
        
    }
    
    public static Boolean CheckIsPased(Cargo cargo){
        Boolean g = false;
        if(cargo.getPocketNomenclature().size()  == 0 && cargo.getPalletWrapper().size()==0){
            return false;
        }
        
        g = cargo.getPocketNomenclature().size()  == 0?true:false;
        
        if(g){
           if(cargo.getPalletWrapper().size()!=0){
               for (Pallet element : cargo.getPalletWrapper()) {
                   g = element.getNomenclatureWrapper().size()  == 0?false:true;
                   if(g)break;
               } 
           }else{
               g = false;
           }
           
        }
        return g;
    }
    
    public static List<String> validBarcodes(List<String> lb){
        List<String> tmplb = new ArrayList<String>();
        for (String element : lb) {
            tmplb.add(element.replaceAll(" ", ""));
        }
        return tmplb;
    }
     public static Double tryToDouble(Object obj){
        Double result = 0.0;
        
        try {
            result = (Double) obj;
        } catch (Exception e) {
            try {
                Integer intobg = obj!= null ? (Integer)obj : 0;
                result = intobg.doubleValue();
            } catch (Exception e2) {
                try {
                    String stronj = obj != null ? (String) obj : "0";
                    Integer intobg = Integer.getInteger(stronj);
                    result = intobg.doubleValue();
                } catch (Exception e3) {
                    logger.error(obj);
                }
            }
        }
        
        return result;
    }
    
    
}
