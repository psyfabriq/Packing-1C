package pfq.pakingserver.dao;

import java.util.List;

import pfq.pakingserver.model.PrinterMessage;

public interface OrderDao {
    boolean addOrder(PrinterMessage pm);
    boolean editOrder(PrinterMessage pm);
    boolean deleteOrder(String id);
    PrinterMessage findOrder(String id);
    List<PrinterMessage> getAllOrder();
}
