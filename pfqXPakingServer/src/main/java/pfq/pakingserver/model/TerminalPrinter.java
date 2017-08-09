package pfq.pakingserver.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "terminal")
public class TerminalPrinter implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Indexed
    private String id   = "";
    private String name = "";
    @Indexed
    private String idSklad   = "";
    private String nameSklad = "";
    
    public TerminalPrinter() {
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    
    
    

}
