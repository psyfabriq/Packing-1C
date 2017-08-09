package pfq.printer.terminal.configuration;



import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Value("${pfq.terminal.name}")
	String name;
	
	@Value("${pfq.terminal.guid}")
	String guid;
	
	@Value("${pfq.server.address}")
	String Server;
	
	@Value("${pfq.server.app}")
	String App;
	
	@Value("${pfq.server.port}")
	Integer Port;
	
	@Value("${pfq.terminal.login}")
	String Login;
	
	@Value("${pfq.terminal.password}")
	String Pasword;
	
	@Value("${pfq.server.idsklad}")
	String idSklad;
	
	@Value("${pfq.server.namesklad}")
	String nameSklad;
	
	@Value("${pfq.terminal.isconfigured}")
	Boolean avaliable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String  getServer() {
		return Server.toString();
	}

	public void setServer(String  server) {
		Server = server;
	}
	

	public String getApp() {
		return App;
	}

	public void setApp(String app) {
		App = app;
	}

	public Integer getPort() {
		return Port;
	}

	public void setPort(Integer port) {
		Port = port;
	}


	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPasword() {
		return Pasword;
	}

	public void setPasword(String pasword) {
		Pasword = pasword;
	}
	
	public Boolean getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Boolean avaliable) {
		this.avaliable = avaliable;
	}
	
	public String getIdSklad() {
		return idSklad;
	}

	public void setIdSklad(String idSklad) {
		this.idSklad = idSklad;
	}

	public String getNameSklad() {
		return nameSklad;
	}

	public void setNameSklad(String nameSklad) {
		this.nameSklad = nameSklad;
	}

	@Override
    public String toString() {
        return "DefaultProperties{" +
                "address='" + Server + '\'' +
                "app='" + App + '\'' +
                "port='" + Port + '\'' +
                "idsklad='" + idSklad + '\'' +
                "namesklad='" + nameSklad + '\'' +
                "terminal name='" + name + '\'' +
                "terminal guid='" + guid + '\'' +
                ", username='" + Login + '\'' +
                '}';
    }
	
	
}
