package pfq.pakingserver.service;

import java.util.List;
import java.util.Map;

import pfq.pakingserver.model.PrinterMessage;

public interface PrinterService {
    public String addTerminal(Map<String, Object> map);
    public String removeTerminal(Map<String, Object> map);
    public String addOrder(Map<String, Object> map);
    public String removeOrder(Map<String, Object> map);
    public Map<String,Object> getPalletToPrint(String idpallet);
    public List<PrinterMessage> getAllOrders();
}
