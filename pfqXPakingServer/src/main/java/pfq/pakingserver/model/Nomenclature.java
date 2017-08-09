package pfq.pakingserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;


@SuppressWarnings("serial")
public class Nomenclature implements Serializable,Cloneable {
    
    String article;
    String name;
    String vesname;
    String EX;
    Double ves;
    boolean accuracy;
    Double  maxcol;
    Double selectedcol;
    List<String> barcode;
    boolean passed;
    //@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    Date  dateAdd;
    //@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    Date  datePassed;
    
    
    public Nomenclature() {
        super();
        this.barcode = new ArrayList<String>();
        this.dateAdd = new Date(System.currentTimeMillis());
    }
    
    @Override
    public Nomenclature clone() throws CloneNotSupportedException {
      return (Nomenclature)super.clone();
    }    
    
    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMaxcol() {
        return maxcol;
    }
    public void setMaxcol(Double maxcol) {
        this.maxcol = maxcol;
    }
    public Double getSelectedcol() {
        return selectedcol;
    }
    public void setSelectedcol(Double selectedcol) {
        this.selectedcol = selectedcol;
    }
    public List<String> getBarcode() {
        return barcode;
    }
    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }
    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
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

    public String getVesname() {
        return vesname;
    }

    public void setVesname(String vesname) {
        this.vesname = vesname;
    }

    public String getEX() {
        return EX;
    }

    public void setEX(String eX) {
        EX = eX;
    }

    public Double  getVes() {
        return ves;
    }

    public void setVes(Double  ves) {
        this.ves = ves;
    }

    public boolean isAccuracy() {
        return accuracy;
    }

    public void setAccuracy(boolean accuracy) {
        this.accuracy = accuracy;
    }
    
   
     
}