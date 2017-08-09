package pfq.pakingserver.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class PrinterMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Indexed
    private String id   = "";
    private String guid = "";
    private String name = "";
    private String work = "";
    private Object data = "";

    
    private Date time = new Date();
    
    public PrinterMessage() {
      
    }
    
    public PrinterMessage(String guid,String name,String work,Object data,boolean result ) {
      this.guid         = guid;
      this.name         = name;
      this.work         = work;
      this.data         = data;

      
    }
    
   // private DateFormat df = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
    
   
    
    public Date getTime() {
      return time;
    }
    public void setTime(Date time) {
      this.time = time;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}