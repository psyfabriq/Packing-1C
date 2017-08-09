package pfq.pakingserver.dao;

import java.util.List;

import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Pallet;

public interface PalletDao {
    void setParrent(Cargo cargo);
    
    
    boolean addPallet(Pallet pallet);
    boolean addPallet(int key,String barcode,String person);
    
    boolean editPallet(Pallet pallet);
    
    boolean deletePallet(Pallet pallet);
    boolean deletePallet(String barcode);
    
  //  boolean setPassed(int key, boolean passed);
  //  boolean setPassed(String  barcode, boolean passed);
    
    boolean checkHasPallet(String barcode);
    boolean checkHasPallet(int key);
 
    Pallet findPallet(int key);
    Pallet findPallet(String  barcode);
    
    List<Pallet> allPalets();
    
    
}
