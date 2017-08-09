package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cargo")
@SuppressWarnings("serial")
public class Cargo implements Serializable,Cloneable  {
    @Indexed
    String id;
    String barcode;
    
    String idOrderShipment;
    String nameOrderShipment;
    
    String numberKis;
    String idSklad;
    String nameSklad;
    String namePersonO;
    String nameClient;
    String nameClientP;
    
    boolean passed;
   // @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    Date  dateAdd;
   // @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    Date  datePassed;
    
    Date  dateOut; 
    
    List<Pallet> palletwrapper;
    List<Nomenclature> pocketNomenclature;
    
    
      

    public Cargo() {
        super();
        this.palletwrapper = new ArrayList<Pallet>();
        this.pocketNomenclature = new ArrayList<Nomenclature>(); 
        this.dateAdd =new Date(System.currentTimeMillis());
        this.barcode = "";
        this.id = "";
        this.idOrderShipment="";
        this.nameOrderShipment="";
        this.numberKis="";
        this.idSklad="";
        this.nameSklad="";
        this.namePersonO="";
        this.nameClient="";
        
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }    
  
    public String getId() {
        return id!=null?id:"";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode!=null?barcode:"";
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public List<Pallet> getPalletWrapper() {
        return palletwrapper;
    }
    public void setPalletWrapper(List<Pallet> palletwrapper) {
        this.palletwrapper = palletwrapper;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public Date getDatePassed() {
        return datePassed;
    }

    public void setDatePassed(Date datePassed) {
        this.datePassed = datePassed;
    }

    public List<Nomenclature> getPocketNomenclature() {
        return pocketNomenclature;
    }

    public void setPocketNomenclature(List<Nomenclature> pocketNomenclature) {
        this.pocketNomenclature = pocketNomenclature;
    }

    public String getIdOrderShipment() {
        return idOrderShipment;
    }

    public void setIdOrderShipment(String idOrderShipment) {
        this.idOrderShipment = idOrderShipment;
    }

    public String getNameOrderShipment() {
        return nameOrderShipment;
    }

    public void setNameOrderShipment(String nameOrderShipment) {
        this.nameOrderShipment = nameOrderShipment;
    }

    public String getNumberKis() {
        return numberKis;
    }

    public void setNumberKis(String numberKis) {
        this.numberKis = numberKis;
    }

    public String getIdSklad() {
        return idSklad;
    }

    public void setIdSklad(String idSklad) {
        this.idSklad = idSklad;
    }

    public String getNameSklad() {
        return nameSklad;
    }

    public void setNameSklad(String nameSklad) {
        this.nameSklad = nameSklad;
    }

    public String getNamePersonO() {
        return namePersonO;
    }

    public void setNamePersonO(String namePersonO) {
        this.namePersonO = namePersonO;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameClientP() {
        return nameClientP;
    }

    public void setNameClientP(String nameClientP) {
        this.nameClientP = nameClientP;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }
    
    
    
    
    
}

