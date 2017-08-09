package pfq.printer.terminal.service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pfq.printer.terminal.configuration.AppConfig;
import pfq.printer.terminal.util.AppUtil;
import pfq.printer.terminal.util.PFQloger;

@Service
public class TerminalServiceImpl implements TerminalService {
	
   private Logger logger = PFQloger.getLogger(TerminalService.class, Level.ALL);
   
   private final static int MAXTRY = 10;
   private int TRY = 0;
   
   private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
   private WebSocketStompClient stompClient ;
   
   Boolean isConnected=false;
   
	
	@Autowired
	private AppConfig dp;
	
	@Autowired
	private SGeneralService sg;

	@Override
	public boolean start() {
        
		if (!isConnected) {
			ListenableFuture<StompSession> f = connect();

			try {
				Thread.sleep(6000);
				StompSession stompSession = f.get();
				System.out.println("Subscribing to greeting topic using session " + stompSession);
				subscribeGreetings(stompSession);
				TRY = 0;
				
			} catch (ExecutionException e) {
				isConnected = false;
				if (TRY < MAXTRY) {
					++TRY;
					start();
				}
			} catch (InterruptedException e) {
				isConnected = false;
				if (TRY < MAXTRY) {
					++TRY;
					start();
				}
			}
		}

		return isConnected;
	}

	@Override
	public boolean stop() {
		stompClient.stop();
		isConnected = false;
		return false;
	}

	private ListenableFuture<StompSession> connect() {

		Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
		List<Transport> transports = Collections.singletonList(webSocketTransport);

		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

		String url = "ws://" + dp.getServer() + ":" + dp.getPort() + "/" + dp.getApp() + "/ws";
		return stompClient.connect(url, headers, new MyHandler());
	}

	private class MyHandler extends StompSessionHandlerAdapter {

		public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
			System.out.println("Now connected");
			isConnected = true;
		}

		public void handleTransportError(StompSession stompSession, Throwable throwable) {
			if (throwable instanceof ConnectionLostException) {
				isConnected = false;
			}
		}
	}

	public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
		stompSession.subscribe("/topic/price", new StompFrameHandler() {

			public Type getPayloadType(StompHeaders stompHeaders) {
				return byte[].class;
			}

			@SuppressWarnings("unchecked")
			public void handleFrame(StompHeaders stompHeaders, Object o) {
				ObjectMapper mapper = new ObjectMapper();
				
				ArrayList<Map<String, Object>> obj;
				
				try {
					obj = mapper.readValue(new String((byte[]) o), ArrayList.class);
					
					for (Map<String, Object> map : obj) {
						if(((String)map.get("guid")).equals(dp.getGuid())){
							sg.addOrder((String)map.get("id"), map.get("data"));
						}
					}
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
			}
		});
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	

}
