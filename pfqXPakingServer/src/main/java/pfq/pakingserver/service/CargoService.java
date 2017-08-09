package pfq.pakingserver.service;

import java.util.List;
import java.util.Map;

import pfq.pakingserver.model.Cargo;

public interface CargoService {
    public String add(Map<String, Object> map);
    public String remove(Map<String, Object> map);
    public String getCargo(Map<String, Object> map);
    public String getAllCargos(Map<String, Object> map);
    public String addPallet(Map<String, Object> map);
    public String removePallet(Map<String, Object> map);
    public String getPallet(Map<String, Object> map);
    public String getAllPallets(Map<String, Object> map);
    public String addPocketNGroup(Map<String, Object> map);
    public String setNomenclatureToPallet(Map<String, Object> map);
    public String setNomenclatureToPocketFromPallet(Map<String, Object> map);
    public String setNomenclatureToPalletGroup(Map<String, Object> map);
    public String setNomenclatureToPocketFromPalletGroup(Map<String, Object> map);
    public String getNomenclatureInfo(Map<String, Object> map);
    public String getPakingInfo(Map<String, Object> map);
    public String getStatusPakingAll(Map<String, Object> map);
    public String getListArticulsSelected(Map<String, Object> map);
    public String getNomenclatureInfoAll(Map<String, Object> map);
    public List<Cargo> listCargo();
}
