package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SkladWrapper implements Serializable,Cloneable {
    private List<Sklad> skladWrapper;
    
    public SkladWrapper(){
        super();
        this.skladWrapper = new ArrayList<Sklad>();
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }    

    public List<Sklad> getSkladWrapper() {
        return skladWrapper;
    }

    public void setSkladWrapper(List<Sklad> skladWrapper) {
        this.skladWrapper = skladWrapper;
    }
    
    

}
