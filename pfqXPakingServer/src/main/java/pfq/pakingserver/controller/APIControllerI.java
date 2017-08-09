package pfq.pakingserver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pfq.pakingserver.model.Cargo;

public interface APIControllerI {
    
    @RequestMapping(value = "/add-cargo", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addCargo(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/rm-cargo", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeCargo(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-cargo", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCargo(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-cargo-full", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCargoFull(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/all-cargos", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllCargos(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/all-cargos-full", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllCargosFull(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/add-pallet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addPallet(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/rm-pallet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removePallet(@RequestBody String json, HttpServletResponse response);  
    
    @RequestMapping(value = "/get-pallet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getPallet(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-pallet-full", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getPalletFull(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/all-pallets", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllPallets(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/all-pallets-full", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllPalletsFull(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/add-pocket-nomenclature-group", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addPocketNomenclatureGroupN(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/set-nomenclature-to-pallet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> setNomenclatureToPallet(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/set-nomenclature-to-pocket", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> setNomenclatureToPocket(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/set-nomenclature-to-pallet-group", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> setNomenclatureToPalletGroup(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/set-nomenclature-to-pocket-group", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> setNomenclatureToPocketGroup(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-nomenclature-info", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getNomenclatureInfo(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-paking-info", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getPakingInfo(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-status-paking-all", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getStatusPakingAll(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-list-articuls-selected", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getListArticulsSelected(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/get-nomenclature-info-all", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getNomenclatureInfoAll(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/add-terminal", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addTerminal(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/remove-terminal", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeTerminal(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value = "/print-pallet", method = RequestMethod.POST,headers="Accept=*/*",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> printPallet(@RequestBody String json, HttpServletResponse response);
    
    @RequestMapping(value="/get-print-document/{orderid}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView  getPrintDocument(HttpServletRequest request, HttpServletResponse response,ModelMap model, @PathVariable String orderid);
    
    @RequestMapping(value="/get-print-document/{orderid}/pdf", method = RequestMethod.GET)
    public @ResponseBody void  getPrintDocumentPdf(HttpServletRequest request, HttpServletResponse response,ModelMap model, @PathVariable String orderid);
    
    @RequestMapping(value="/all-cargo-get", method = RequestMethod.GET)
    public @ResponseBody List<Cargo> allCargo(ModelMap model);
    
    
}
