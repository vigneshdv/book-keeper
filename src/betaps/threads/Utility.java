package betaps.threads;

import betaps.model.StudentFee;
import java.io.File;
import java.io.IOException;
import nu.xom.*;

/**
 *
 * @author Vignesh
 */
public class Utility {

    public static String invoiceNumber;
    public static StudentFee details;

    public static void readandsetInvoiceNumber() {

        try {
            Builder parser = new Builder(false);
            File settingsFile = new File("data" + File.separator + "settings.xml");
            Document doc = parser.build(settingsFile);

            Element settings = doc.getRootElement();
            Element invoice = (Element) settings.getFirstChildElement("Invoice");
            Element serial = invoice.getFirstChildElement("Last-Used-Serial");

            Utility.invoiceNumber = serial.getValue();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParsingException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeInvoiceToFile() {
        try {
            Builder parse = new Builder(false);
            File dataFile = new File("data" + File.pathSeparator + "Class_" + details.getStudentClass() + ".xml");
            Document doc = parse.build(dataFile);
            
            Element root = doc.getRootElement();
            
            
            
            
            
            System.out.println("Here: " + root.toXML());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
