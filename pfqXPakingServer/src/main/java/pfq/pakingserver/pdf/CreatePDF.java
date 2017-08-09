package pfq.pakingserver.pdf;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.io.Resource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class CreatePDF {
    
  //  final BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    
    private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
 
    /**
     * @param args
     */
    public static Document createPDF(String file, String path )  throws IOException, DocumentException {
 
        Document document = null;
 
     
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
 
            addMetaData(document);
            
            
 
          //  addTitlePage(document);
 
         //   createTable(document);
            
        //  InputStream stream = new ByteArrayInputStream(templateT().getBytes(StandardCharsets.UTF_8));
            
          
            
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(path));
 
            document.close();
 
        return document;
 
    }
 
    

    
    
    
    private static void addMetaData(Document document) {
        document.addTitle("Generate PDF report");
        document.addSubject("Generate PDF report to printer terminal");
        document.addAuthor("PsyFabriQ DEV");
        document.addCreator("Create By XPakingServer");
    }
 
    private static void addTitlePage(Document document)throws DocumentException {
       /*
        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph("PDF Report", TIME_ROMAN));
 
        creteEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        preface.add(new Paragraph("Report created on "
                + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
        document.add(preface);
     */
        
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(2);
        
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        
        table.addCell(new Paragraph("Заказ №:", TIME_ROMAN));
        table.addCell("882");
        
        table.addCell("Дата:");
        table.addCell("9 декабря 2016 г.");
        
        table.addCell("Заказчик:");
        table.addCell("БАИК РУС ООО");
        
        table.addCell("Получатель:");
        table.addCell("Браммер");
        
        table.addCell("Порядковый номер груза:");
        table.addCell("1/10");
        
        table.addCell("Масса грузового места (брутто, кг):");
        table.addCell("18,61");
        
        document.add(table);
        
    }
 
    private static void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
 
    private static void createTable(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
       // creteEmptyLine(paragraph, 2);
       // document.add(paragraph);
        PdfPTable table = new PdfPTable(6);
 
        PdfPCell c1 = new PdfPCell(new Phrase("№"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
 
        c1 = new PdfPCell(new Phrase("Наименование"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
 
        c1 = new PdfPCell(new Phrase("Артикул"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("ед. измерения"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Кол-во"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Вес Ед"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
 
        for (int i = 0; i < 5; i++) {
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            
         
            
            table.addCell(Integer.toString(i+1));
            table.addCell("наименование тест");
            table.addCell("347436538457");
            table.addCell("шт");
            table.addCell("3");
            table.addCell("5,8");
        }
 
        document.add(table);
    }
 
}
