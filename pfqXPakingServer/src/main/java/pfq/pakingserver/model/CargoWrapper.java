package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CargoWrapper implements Serializable,Cloneable {
    
    List<Cargo> cargowrapper ;
    

    public CargoWrapper() {
        super();
        this.cargowrapper=new ArrayList<Cargo>();
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }    

    public List<Cargo> getCargoWrapper() {
        return cargowrapper;
    }

    public void setCargoWrapper(List<Cargo> cargowrapper) {
        this.cargowrapper = cargowrapper;
    }
    
    

}
