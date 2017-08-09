package pfq.pakingserver.dao;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pfq.pakingserver.AppUtil;
import pfq.pakingserver.PFQloger;
import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Pallet;

@Repository
public class PalletDaoImpl implements PalletDao {

    private Logger logger = PFQloger.getLogger(PalletDao.class, Level.ALL);
   
    @Autowired
    NomenclatureDao nomenclatureDao;
    
    private List<Pallet> palletWrapper;
    private Pallet tmp;

    @Override
    public void setParrent(Cargo cargo) {
        logger.info("setParrent");
        this.palletWrapper = cargo.getPalletWrapper();
    }
    
    @Override
    public boolean addPallet(Pallet pallet) {
        logger.info("addPallet 1");
        boolean result = !checkHasPallet(pallet.getKey())? !checkHasPallet(pallet.getBarcode()) : false;
        if (result) {
            tmp = pallet;
            palletWrapper.add(pallet);
            nomenclatureDao.setParrent(pallet);
        } else {
            AppUtil.setError("Cargo has Pallet on server !!!");
        }
        return result;
    }

    @Override
    public boolean addPallet(int key, String barcode, String person) {
        logger.info("addPallet 2");
        tmp = new Pallet();
        tmp.setKey(key);
        tmp.setBarcode(barcode);
        tmp.setNamePersonS(person);
        nomenclatureDao.setParrent(tmp);
        return addPallet(tmp);
    }

    @Override
    public boolean editPallet(Pallet pallet) {
        logger.info("editPallet");
        boolean result = false;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (pallet.getBarcode().equals(tmp.getBarcode())) {
                listIter.set(pallet);
                result = true;
                nomenclatureDao.setParrent(tmp);
                break;
            }

        }
        return result;
    }

    @Override
    public boolean deletePallet(Pallet pallet) {
        logger.info("deletePallet");
        boolean result = false;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (pallet.getBarcode().equals(tmp.getBarcode())) {
                listIter.remove();
                result = true;
                //nomenclatureDao.setParrent(null); ERRor
                tmp=null;
                break;
            }
        }
        //nomenclatureDao.setParrent(null);
        return result;
    }
    
    @Override
    public boolean deletePallet(String barcode) {
        Pallet pallet = new Pallet();
        pallet.setBarcode(barcode);
        return deletePallet(pallet);
    }
/*
    @Override
    public boolean setPassed(int key, boolean passed) {
        logger.info("setPassed 1");
        boolean result = false;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (tmp.getKey() == key) {
                tmp.setPassed(passed);
                listIter.set(tmp);
                result = true;
                nomenclatureDao.setParrent(tmp);
                break;
            }
        }
        return result;
    }
*/
    /*
    @Override
    public boolean setPassed(String barcode, boolean passed) {
        logger.info("setPassed 2");
        boolean result = false;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (tmp.getBarcode().equals(barcode)) {
                tmp.setPassed(passed);
                listIter.set(tmp);
                result = true;
                nomenclatureDao.setParrent(tmp);
                break;
            }
        }
        return result;
    }
*/
    @Override
    public Pallet findPallet(int key) {
        logger.info("findPallet 1");
        Pallet result = null;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (listIter.next().getKey() == key) {
                nomenclatureDao.setParrent(tmp);
                result = tmp;
                break;
            }
        }
        return result;
    }

    @Override
    public Pallet findPallet(String barcode) {
        logger.info("findPallet 2");
        Pallet result = null;
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (tmp.getBarcode().equals(barcode)) {
                result = tmp;
                nomenclatureDao.setParrent(tmp);
                break;
            }
        }
  
        return result;
    }


    @Override
    public boolean checkHasPallet(String barcode) {
        logger.info("checkHasPallet 1");
        Boolean result = false;
        //nomenclatureDao.setParrent(null);
        ListIterator<Pallet> listIter = palletWrapper.listIterator();
        while (listIter.hasNext()) {
            tmp = listIter.next();
            if (barcode.equals(tmp.getBarcode())) {
                result = true;
                nomenclatureDao.setParrent(tmp);
                break;
            }
        }

        return result;
    }

    @Override
    public boolean checkHasPallet(int key) {
        logger.info("checkHasPallet 2 empty");
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Pallet> allPalets() {
       // nomenclatureDao.setParrent(null); // ERROR ?
        return palletWrapper;
    }

   

   

  

}
