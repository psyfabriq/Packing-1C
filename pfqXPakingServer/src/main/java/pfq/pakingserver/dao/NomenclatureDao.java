package pfq.pakingserver.dao;

import java.util.List;
import java.util.Map;

import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

public interface NomenclatureDao {
    
    void setParrent(Pallet pallet);
    
    boolean setNomenclatureList(List<Nomenclature> ln );
    
    boolean setNomenclatureListMap( List<Map<String, Object>> ln );
    
    boolean addNomenclature(Map<String, Object> nomenclature, boolean merge);
    
    boolean addNomenclature(Nomenclature nomenclature, boolean merge);
    
    boolean checkHasNomenclature(Nomenclature nomenclature,  List<Nomenclature> listNomeclature, boolean merge);
    
    boolean removeNomenclature(Nomenclature nomenclature);
    
    List<Nomenclature> getListNomenclature();
    
    Nomenclature findNomenclature(String barcodeArticle);
    

}
