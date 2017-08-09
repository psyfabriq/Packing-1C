package pfq.pakingserver.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "sklad")
public class Sklad implements Serializable,Cloneable{
    @Indexed
    String id;
    String name;
    CargoWrapper cargoWrapper ;
    
    public Sklad(){
        super();
        this.id="";
        this.name="";
        this.cargoWrapper = new CargoWrapper();
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
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
    
    

}
