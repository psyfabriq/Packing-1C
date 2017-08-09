package pfq.printer.terminal.service;

import java.util.Map;

public interface AppService {
	 public String setProperoties(Map<String, Object> map);
	 public String getProperoties(Map<String, Object> map);
	 public String getInstaledPrinters(Map<String, Object> map);
	 public String getDocumetToPrint(String idDocument);
}
