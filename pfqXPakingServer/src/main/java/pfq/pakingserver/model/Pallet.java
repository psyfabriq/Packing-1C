package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class Pallet implements Serializable,Cloneable {
    int key;
  //  boolean passed;
    @Indexed
    String barcode;
    //@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    Date  dateAdd;
    //@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
  //  Date  datePassed;
    List<Nomenclature> nomenclaturewrapper;
    
    String namePersonS;
    
    
    
    public Pallet() {
        super();
        this.nomenclaturewrapper=new ArrayList<Nomenclature>();
        this.dateAdd = new Date(System.currentTimeMillis());
        this.barcode = "";
        this.namePersonS = "";
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }    
    
    public Date getDateAdd() {
        return dateAdd;
    }

   // public Date getDatePassed() {
    //    return datePassed;
   // }

   // public void setDatePassed(Date datePassed) {
   //     this.datePassed = datePassed;
   // }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

 //   public boolean isPassed() {
 //       return passed;
 //   }

  //  public void setPassed(boolean passed) {
   //     this.passed = passed;
   // }

    public List<Nomenclature> getNomenclatureWrapper() {
        return nomenclaturewrapper;
    }

    public void setNomenclatureWrapper(List<Nomenclature> nomenclaturewrapper) {
        this.nomenclaturewrapper = nomenclaturewrapper;
    }

    public String getNamePersonS() {
        return namePersonS;
    }

    public void setNamePersonS(String namePersonS) {
        this.namePersonS = namePersonS;
    }
    
    
    
}
