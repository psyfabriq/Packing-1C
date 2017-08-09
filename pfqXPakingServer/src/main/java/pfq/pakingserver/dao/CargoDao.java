package pfq.pakingserver.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import pfq.pakingserver.model.Cargo;
import pfq.pakingserver.model.Nomenclature;
import pfq.pakingserver.model.Pallet;

public interface CargoDao {
    boolean addCargo(Cargo cargo);
    boolean editCargo(Cargo cargo);
    boolean editCargo();
    boolean deleteCargo(Cargo cargo);
    boolean setPassed(String barcode , boolean passed);
    boolean setPassedByID(String ID , boolean passed);
    boolean checkHasCargo(String barcode);
    boolean checkHasCargoByID(String ID);
    boolean cleanPocket();
    boolean setNomenclatureListTopPocket(String barcode,List<Nomenclature> ln);
    boolean addNomenclatureToPocket(Nomenclature ntp);
    boolean checkHasNomenclatureInPocket(Nomenclature nomenclature, List<Nomenclature> listNomeclature , boolean merge);
    Nomenclature giveNomenclatureFromPocket(String barcodenomenclature,Double col);
    Nomenclature findNomenclatureFromPocket(String barcodenomenclature);
    Cargo findCargo(String barcode);
    Cargo findCargoByID(String ID);
    Cargo findCargoByQueryOne(String query);
    Cargo getCargo();
    List<Cargo> findCargoByQueryList(String query);
    List<Cargo> findCargoByQueryList(Query query);
    List<Pallet> getListPallet(String barcode);
    List<Pallet> getListPalletByID(String ID);
    List<Cargo> getAllCargo();
  
    
}
