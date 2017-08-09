package pfq.printer.terminal.service;

public interface TerminalService {
	public boolean start();
	public boolean stop();
	public Boolean getIsConnected();
}
