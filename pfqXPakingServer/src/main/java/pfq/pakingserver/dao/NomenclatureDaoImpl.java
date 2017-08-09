package pfq.pakingserver.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import pfq.pakingserver.CargoUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

@Repository
public class NomenclatureDaoImpl implements NomenclatureDao{
    
    private Logger logger = PFQloger.getLogger(NomenclatureDao.class, Level.ALL);
    
    private Pallet pallet;
    private List<Nomenclature> nomenclatureWrapper;
    

    @Override
    public void setParrent(Pallet pallet) {
        this.nomenclatureWrapper = pallet.getNomenclatureWrapper();
        this.pallet = pallet;
        
    }
    
    @Override
    public List<Nomenclature> getListNomenclature() {
        logger.info("getListNomenclature");
        return nomenclatureWrapper;
    }
    
    @Override
    public boolean setNomenclatureList(List<Nomenclature> ln) {
        logger.info("setNomenclatureList 1");
        boolean result = false;
        if (pallet != null) {
            pallet.setNomenclatureWrapper(ln);
            result = true;
        }else{logger.error("Pallet null");}
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean setNomenclatureListMap(List<Map<String, Object>> ln) {
        logger.info("setNomenclatureListMap");
        boolean result = false;

        List<Nomenclature> listNomeclature = new ArrayList<Nomenclature>();
        ListIterator<Map<String, Object>> listIter = ln.listIterator();

        while (listIter.hasNext()) {
            Map<String, Object> tmp = listIter.next();
            Nomenclature nomenclature = new Nomenclature();
            nomenclature.setArticle((String) tmp.get("article"));
            nomenclature.setName((String) tmp.get("name"));
            nomenclature.setMaxcol((Double)tmp.get("maxcol"));
            nomenclature.setBarcode((List<String>) tmp.get("barcodes"));
            if(!checkHasNomenclature(nomenclature,listNomeclature,true)){
               listNomeclature.add(nomenclature);
            }
        }

        result = setNomenclatureList(listNomeclature);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addNomenclature(Map<String, Object> tmpmap,boolean merge) {
        logger.info("addNomenclature 1");
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setArticle((String) tmpmap.get("article"));
        nomenclature.setName((String) tmpmap.get("name"));
        nomenclature.setMaxcol((Double)tmpmap.get("maxcol"));
        nomenclature.setBarcode((List<String>) tmpmap.get("barcodes"));
        return addNomenclature(nomenclature,merge);
    }

    @Override
    public boolean addNomenclature(Nomenclature nomenclature,boolean merge) {
        logger.info("addNomenclature 2"); 
        boolean result = false;
        if (pallet != null) {
            if(!checkHasNomenclature(nomenclature,nomenclatureWrapper,merge)){
                nomenclatureWrapper.add(nomenclature);
             }
            result = true;
        }else{logger.error("Pallet null");}
        return result;
    }
    
    @Override
    public boolean checkHasNomenclature(Nomenclature nomenclature, List<Nomenclature> listNomeclature , boolean merge) {
        logger.info("checkHasNomenclature"); 
        boolean result = false;
        ListIterator<Nomenclature> listIter = listNomeclature.listIterator();
        while (listIter.hasNext()) {
            Nomenclature tmp = listIter.next();
            
            if(tmp.getArticle().equals(nomenclature.getArticle())){
                result = true;
                tmp.setMaxcol(tmp.getMaxcol()+nomenclature.getMaxcol());
                tmp.setSelectedcol(tmp.getSelectedcol()+nomenclature.getSelectedcol());
                
                if(!tmp.getBarcode().equals(nomenclature.getBarcode())){
                    List<String> barcodes = nomenclature.getBarcode();
                    for (int i = 0; i < barcodes.size(); i++) {
                        if(!tmp.getBarcode().contains(barcodes.get(i))){
                            tmp.getBarcode().add(barcodes.get(i));
                        }
                    }
                }
                listIter.set(tmp);
                break;
            }
            
        }
        return result;
    }

    @Override
    public Nomenclature findNomenclature(String barcodeArticle) {
        Nomenclature result = null;
        ListIterator<Nomenclature> listIter = nomenclatureWrapper.listIterator();
       
        while (listIter.hasNext()) {
            Nomenclature tmp = listIter.next();
            tmp.setBarcode(CargoUtil.validBarcodes(tmp.getBarcode()));
            if(tmp.getArticle().equals(barcodeArticle)){
                result = tmp;
                break;
            }else if(tmp.getBarcode().contains(barcodeArticle)){
                result = tmp;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean removeNomenclature(Nomenclature nomenclature) {
        boolean result = false;
        if (pallet != null) {
                nomenclatureWrapper.remove(nomenclature);
            result = true;
        }else{logger.error("Pallet null");}
        return result;
    }

    

}
