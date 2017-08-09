package pfq.pakingserver.dao;

import java.util.List;

import pfq.pakingserver.model.PrinterMessage;
import pfq.pakingserver.model.TerminalPrinter;

public interface TerminalDao {
    boolean addTerminal (TerminalPrinter tp);
    boolean deleteTerminal(String id);
    TerminalPrinter findTerminal(String skladId);
    List<TerminalPrinter> getAllTerminal();
}
