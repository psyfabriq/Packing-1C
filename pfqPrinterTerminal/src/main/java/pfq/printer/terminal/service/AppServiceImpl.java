package pfq.printer.terminal.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pfq.printer.terminal.configuration.AppConfig;
import pfq.printer.terminal.controllers.ActionController;
import pfq.printer.terminal.util.AppUtil;
import pfq.printer.terminal.util.PFQloger;
import pfq.printer.terminal.util.ResponseStatus;

@Service
public class AppServiceImpl implements AppService {

	private Logger logger = PFQloger.getLogger(ActionController.class, Level.ALL);

	@Autowired
	private AppConfig dp;

	@Autowired
	private TerminalService terminalService;
	
	@Override
	public String setProperoties(Map<String, Object> map) {

		if (map.get("nameTerminal") != null)
			dp.setName((String) map.get("nameTerminal"));
		if (map.get("guidTerminal") != null)
			dp.setGuid((String) map.get("guidTerminal"));
		if (map.get("serverAddress") != null)
			dp.setServer((String) map.get("serverAddress"));
		if (map.get("serverApp") != null)
			dp.setApp((String) map.get("serverApp"));
		if (map.get("idSklad") != null)
			dp.setIdSklad((String) map.get("idSklad"));
		if (map.get("nameSklad") != null)
			dp.setNameSklad((String) map.get("nameSklad"));
		if (map.get("serverPort") != null)
			dp.setPort((Integer) map.get("serverPort"));

		if (map.get("login") != null && !((String) map.get("login")).isEmpty())
			dp.setLogin((String) map.get("login"));
		if (map.get("password") != null && !((String) map.get("password")).isEmpty())
			dp.setPasword((String) map.get("password"));

		Map<String, Object> mapobj = new HashMap<String, Object>();

		mapobj.put("id", dp.getGuid());
		mapobj.put("name", dp.getName());
		mapobj.put("idsklad", dp.getIdSklad());
		mapobj.put("namesklad", dp.getNameSklad());

		ObjectMapper objmapper = new ObjectMapper();
		try {
			String json = objmapper.writeValueAsString(mapobj);

			String urlString = "http://" + dp.getServer() + ":" + dp.getPort() + "/" + dp.getApp() + "/add-terminal";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> loginResponse = restTemplate.exchange(urlString, HttpMethod.POST, entity,String.class);

			System.out.println(loginResponse.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		dp.setAvaliable(true);

		return AppUtil.getResponseJson(ResponseStatus.OK);
	}

	@Override
	public String getProperoties(Map<String, Object> map) {
		map = new HashMap<String, Object>();
		map.put("GUID", dp.getGuid());
		map.put("NAME", dp.getName());
		map.put("LOGIN", dp.getLogin());
		map.put("SERVER", dp.getServer());
		map.put("SERVERAPP", dp.getApp());
		map.put("IDSKLAD", dp.getIdSklad());
		map.put("NAMESLAD", dp.getNameSklad());
		map.put("PORT", dp.getPort());
		map.put("STATUS", terminalService.getIsConnected() ? "STARTED" : "STOPTED");
		return AppUtil.getResponseJson(map, ResponseStatus.OK);
	}

	@Override
	public String getInstaledPrinters(Map<String, Object> map) {
		map = new HashMap<String, Object>();
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		 System.out.println("Number of print services: " + printServices.length);

		for (PrintService printer : printServices) {
			map.put("NAME", printer.getName());
		}
		
		 String defaultPrinter  =   PrintServiceLookup.lookupDefaultPrintService().getName();
		 map.put("NAME", defaultPrinter);
	        
		return AppUtil.getResponseJson(map, ResponseStatus.OK);
	}

	@Override
	public String getDocumetToPrint(String idDocument) {
		String urlString = "http://" + dp.getServer() + ":" + dp.getPort() + "/" + dp.getApp() + "/get-print-document/"+ idDocument+"/pdf";
	    System.out.println(urlString);
		 try {
			URL url = new URL(urlString);
			
			PrintService  myService  =   PrintServiceLookup.lookupDefaultPrintService();
			Doc pdfDoc = new SimpleDoc(url, DocFlavor.URL.AUTOSENSE, null);
			DocPrintJob printJob = myService.createPrintJob();
			printJob.print(pdfDoc, new HashPrintRequestAttributeSet());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  
	      catch (PrintException e) {
			e.printStackTrace();
		}

		return null;
	}

}
