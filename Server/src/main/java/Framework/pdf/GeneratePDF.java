/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Framework.pdf;

import Framework.data.DataLayerException;
import Model.Bean.Resoconto;
import Model.Bean.Studente;
import Model.DAO.Impl.TirocinanteDAOImpl;
import Model.DAO.Interface.TirocinanteDAO;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 *
 * @author lorenzo
 */
public class GeneratePDF {
    
        public static int resocontoTirocinio(Resoconto resoconto, Studente studente) throws IOException, DataLayerException, DocumentException {

        TirocinanteDAO tirocinioDAO = new TirocinanteDAOImpl();

        // Carico modello base resoconto
        InputStream is = tirocinioDAO.downloadResoconto(new Resoconto(0));
        // We create a reader with the InputStream
        PdfReader reader = new PdfReader(is, null);
        // We create an OutputStream for the new PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Now we create the PDF
        PdfStamper stamper = new PdfStamper(reader, baos);
        // We alter the fields of the existing PDF

        AcroFields fields = stamper.getAcroFields();
        fields.setGenerateAppearances(true);

        Set<String> parameters = fields.getFields().keySet();

        // Fill field
        fields.setField("nomeTirocinante", studente.getCognome()+" "+studente.getNome());
        fields.setField("Telefono", studente.getTelefono());
        
        //Flatt form
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();

        is = new ByteArrayInputStream(baos.toByteArray());
        is.close();

        resoconto.setNome("resoconto");
        resoconto.setPeso(is.available());
        resoconto.setEstensione("application/pdf");
        resoconto.setFile(is);

        return tirocinioDAO.uploadResoconto(resoconto);
    }
}
