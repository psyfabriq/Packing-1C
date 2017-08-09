package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.List;



@SuppressWarnings("serial")
public class NomenclatureWrapper implements Serializable,Cloneable{

    List<Nomenclature>  nomrnclature;
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }    

    public List<Nomenclature> getNomrnclature() {
        return nomrnclature;
    }

    public void setNomrnclature(List<Nomenclature> nomrnclature) {
        this.nomrnclature = nomrnclature;
    }
    
    
}
