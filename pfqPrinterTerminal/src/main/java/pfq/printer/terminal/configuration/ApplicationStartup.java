package pfq.printer.terminal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pfq.printer.terminal.service.TerminalService;

@Component
public class ApplicationStartup 
implements ApplicationListener<ContextRefreshedEvent> {

  /**
   * This method is called during Spring's startup.
   * 
   * @param event Event raised when an ApplicationContext gets initialized or
   * refreshed.
   */
	@Autowired
	private TerminalService terminalService;
	
  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
 
	  terminalService.start();
 
    return;
  }
 
} 
