package pfq.pakingserver.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.service.CargoService;
import pfq.pakingserver.service.PrinterService;

@RestController
@RequestMapping("/")
public class APIController implements APIControllerI {

    private Logger logger = PFQloger.getLogger(APIController.class, Level.ALL);
    private static final HttpHeaders head = new HttpHeaders();
    private Map<String, Object> map;
    
    @Autowired
    ApplicationContext act;

    @Autowired
    private CargoService cargoService;
    
    @Autowired
    private PrinterService printerService;

    public APIController() {
        super();
        head.add("Content-type",MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
    }

    /*
     * @see /all-cargo-get GET No Parameters
     */
    @Override
    public @ResponseBody List<Cargo> allCargo(ModelMap model) {
        return cargoService.listCargo();
    }

    /*
     * @see /add-cargo POST {"id":"#####","barcode":"######","idordershipment":"#####","nameordershipment":"######",
     * @see                  "idsklad":"#####","namesklad":"######","nameperson":"#####","numberkis":"######",
     *  @see                 "nameclient":"#####"}
     */
    @Override
    public ResponseEntity<String> addCargo(@RequestBody String json,HttpServletResponse response) {
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.add(map), head,HttpStatus.OK);
    }

    /*
     * @see /rm-cargo POST {"id":"#####","barcode":"######"}
     */
    @Override
    public ResponseEntity<String> removeCargo(@RequestBody String json,HttpServletResponse response) {
        logger.debug("removeCargo");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.remove(map), head, HttpStatus.OK);
    }

    /*
     * @see /get-cargo-full POST {"barcode":"######","html":false}
     */
    @Override
    public ResponseEntity<String> getCargoFull(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getCargoFull");
        map = AppUtil.getValues(json);
        map.put("full", true);
        return new ResponseEntity<String>(cargoService.getCargo(map), head,HttpStatus.OK);
    }

    /*
     * @see /get-cargo POST {"barcode":"######","html":false}
     */
    @Override
    public ResponseEntity<String> getCargo(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getCargo");
        map = AppUtil.getValues(json);
        map.put("full", false);
        return new ResponseEntity<String>(cargoService.getCargo(map), head,HttpStatus.OK);
    }

    /*
     * @see /all-cargos POST No Parameters
     */
    @Override
    public ResponseEntity<String> getAllCargos(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getAllCargos");

        map = AppUtil.getValues(json);
   
        map.put("full", false);
        return new ResponseEntity<String>(cargoService.getAllCargos(map),head, HttpStatus.OK);
    }
    
    /*
     * @see /all-cargos-full POST No Parameters
     */
    @Override
    public ResponseEntity<String> getAllCargosFull(@RequestBody String json, HttpServletResponse response) {
        logger.debug("getAllCargosFull");
     
        map = AppUtil.getValues(json);
   
        map.put("full", true);
        return new ResponseEntity<String>(cargoService.getAllCargos(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /add-pallet POST
     * @see {"id":"#####","barcode":"###","pallet":{"key":0,"barcode":"###","person":"###","nomenclatures":[]}}
     * @see "nomenclatures":[{"article":"###","name":"###","maxcol":0,"barcodes":["###","###"]}]
     */
    @Override
    public ResponseEntity<String> addPallet(@RequestBody String json,HttpServletResponse response) {
        logger.debug("addPallet");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.addPallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /rm-pallet POST
     * {"id":"####","barcode":"###","pallet":{"key":"###","barcode":"###"}}
     */
    @Override
    public ResponseEntity<String> removePallet(@RequestBody String json,HttpServletResponse response) {
        logger.debug("removePallet");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.removePallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /get-pallet POST
     * {"id":"####","barcode":"###","pallet":{"key":"###","barcode":"###"}}
     */
    @Override
    public ResponseEntity<String> getPallet(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getPallet");
        map = AppUtil.getValues(json);
        map.put("full", false);
        return new ResponseEntity<String>(cargoService.getPallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /get-pallet-full POST
     * {"id":"####","barcode":"###","pallet":{"key":"###","barcode":"###"}}
     */
    @Override
    public ResponseEntity<String> getPalletFull(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getPalletFull");
        map = AppUtil.getValues(json);
        map.put("full", true);
        return new ResponseEntity<String>(cargoService.getPallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /all-pallets POST {"id":"####","barcode":"###"}
     */
    @Override
    public ResponseEntity<String> getAllPallets(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getAllPallets");
        map = AppUtil.getValues(json);
        map.put("full", false);
        return new ResponseEntity<String>(cargoService.getAllPallets(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /all-pallets-full POST {"id":"####","barcode":"###"}
     */
    @Override
    public ResponseEntity<String> getAllPalletsFull(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getAllPalletsFull");
        map = AppUtil.getValues(json);
        map.put("full", true);
        return new ResponseEntity<String>(cargoService.getAllPallets(map), head,HttpStatus.OK);
    }
    /*
     * @see /add-pocket-nomenclature-group POST {"id":"####","barcode":"###","pocketn":[{"article":"###","name":"###","maxcol":0,"barcodes":["###","###"]}]}
     */
    @Override
    public ResponseEntity<String> addPocketNomenclatureGroupN(@RequestBody String json, HttpServletResponse response) {
        logger.debug("addPocketNomenclatureGroupN");
        //logger.info(json);
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.addPocketNGroup(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /set-nomenclature-to-pallet POST {"id":"####","barcode":"###","barcodepallet":"###","barcodenomeclature":"###","col":0}
     */

    @Override
    public ResponseEntity<String> setNomenclatureToPallet(@RequestBody  String json, HttpServletResponse response) {
        logger.debug("setNomenclatureToPallet");
        //logger.info(json);
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.setNomenclatureToPallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /set-nomenclature-to-pocket POST {"id":"####","barcode":"###","barcodepallet":"###","barcodenomeclature":"###","col":0}
     */
    @Override
    public ResponseEntity<String> setNomenclatureToPocket(@RequestBody  String json,HttpServletResponse response) {
        logger.debug("setNomenclatureToPocket");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.setNomenclatureToPocketFromPallet(map), head,HttpStatus.OK);
    }
    
    /*
     * @see /get-nomenclature-info POST {"barcode":"###","barcodenomeclature":"###"}
     */
    
    @Override
    public ResponseEntity<String> getNomenclatureInfo(@RequestBody  String json,  HttpServletResponse response) {
        logger.debug("getNomenclatureInfo");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.getNomenclatureInfo(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> setNomenclatureToPalletGroup(@RequestBody String json,HttpServletResponse response) {
        logger.debug("setNomenclatureToPalletGroup");
        map = AppUtil.getValues(json);
        
        return new ResponseEntity<String>(cargoService.setNomenclatureToPalletGroup(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> setNomenclatureToPocketGroup(@RequestBody String json,HttpServletResponse response) {
        logger.debug("setNomenclatureToPocketGroup");
        map = AppUtil.getValues(json);
        
        return new ResponseEntity<String>(cargoService.setNomenclatureToPocketFromPalletGroup(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getPakingInfo(@RequestBody String json, HttpServletResponse response) {
        logger.debug("getPakingInfo");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.getPakingInfo(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getStatusPakingAll(@RequestBody String json, HttpServletResponse response) {
        logger.debug("getPakingInfo");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.getStatusPakingAll(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getListArticulsSelected(@RequestBody  String json,HttpServletResponse response) {
        logger.debug("getListArticulsSelected");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.getListArticulsSelected(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getNomenclatureInfoAll(@RequestBody String json, HttpServletResponse response) {
        logger.debug("getNomenclatureInfoAll");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(cargoService.getNomenclatureInfoAll(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addTerminal(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getNomenclatureInfoAll");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(printerService.addTerminal(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeTerminal(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getNomenclatureInfoAll");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(printerService.removeTerminal(map), head,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> printPallet(@RequestBody String json,HttpServletResponse response) {
        logger.debug("getNomenclatureInfoAll");
        map = AppUtil.getValues(json);
        return new ResponseEntity<String>(printerService.addOrder(map), head,HttpStatus.OK);
    }

    @Override
    public ModelAndView  getPrintDocument(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable String orderid) {
        
        Map<String,Object> data = printerService.getPalletToPrint(orderid);
        
        if((boolean)data.get("isFind")){
            
        }
        
        ModelAndView modelv = new ModelAndView("viewprintdoc");
        modelv.addObject("order", data.get("order"));
        modelv.addObject("data", data.get("data"));
        modelv.addObject("z", data.get("z"));
        modelv.addObject("p", data.get("p"));
        modelv.addObject("png", data.get("png"));
        modelv.addObject("mgm", data.get("mgm"));
        modelv.addObject("lists", data.get("lists"+ ""));
        modelv.addObject("allcount", data.get("allcount"));
        modelv.addObject("barcode", data.get("barcode"));
        
        /*
        try {
            File tmpHtml =  File.createTempFile("tmphtml", ".html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmpHtml));
            bw.write(modelv.toString());
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        
        return modelv;
/*
        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();
        
        
        Resource resource =  act.getResource("classpath:pfq/pakingserver/pdf/recource/template.html");
        
        
        try {
            logger.debug(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String fileName = palletid+".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename="+ fileName);
 
        try {
 
            CreatePDF.createPDF(temperotyFilePath+"\\"+fileName,resource.getFile().getAbsolutePath());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = AppUtil.convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
    }

*/
    }

    @Override
    public void  getPrintDocumentPdf(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable String orderid) {
      //  HttpServletRequest currentRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String urlString = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/get-print-document/"+ orderid;
        //String urlString = "https://www.google.ru";
        String pathToPhantomJS = "phantomjs";
        File pathToRasterizeJS = new File(getClass().getClassLoader().getResource("phantomjs/rasterize.js").getFile());
        String paperSize = "A4";
        
        try {
            java.io.File outputFile = java.io.File.createTempFile("sample",".pdf");

            String exec = pathToPhantomJS + " --output-encoding=utf8 --script-encoding=utf8  "
                 + pathToRasterizeJS.getAbsolutePath() + " " + urlString
                 + " " + outputFile.getAbsolutePath() + " " + paperSize;

            // logger.debug(urlString);
               logger.debug(exec);
             logger.debug(pathToRasterizeJS.getAbsolutePath());

            Process process = Runtime.getRuntime().exec(exec);
            int exitStatus = process.waitFor();

            if (exitStatus != 0) {
                logger.info("EXIT-STATUS - " + process.toString());
            }else{
                
                Thread.sleep(1000);

                FileInputStream fileInputStream = new FileInputStream(outputFile);
                
                byte[] bFile = new byte[(int)outputFile.length()];
                
                fileInputStream.read(bFile);
                fileInputStream.close();
                
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=" + orderid+".pdf");
                response.setContentLength(bFile.length);

                response.getOutputStream().write(bFile);
                response.getOutputStream().flush();
                
                
                
                if(outputFile.delete()){
                    logger.debug("remove tmp file )))");
                }
            }

        } catch (IOException e1) {
            logger.error(e1);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        
    }

   

}
