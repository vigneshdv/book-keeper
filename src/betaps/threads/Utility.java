package betaps.threads;

import betaps.model.StudentFee;
import java.io.File;
import java.io.FileOutputStream;
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
            System.out.println("Read and Set\n");
            Builder parser = new Builder(false);
            File settingsFile = new File("data" + File.separator + "settings.xml");
            Document doc = parser.build(settingsFile);

            Element settings = doc.getRootElement();
            Element invoice = (Element) settings.getFirstChildElement("Invoice");
            Element serial = invoice.getFirstChildElement("Last-Used-Serial");

            Utility.invoiceNumber = serial.getValue();
            //System.out.println("Updated Value: " + serial.getValue());

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParsingException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeInvoiceToFile() {
        try {
            Builder parse = new Builder(false);
            File dataFile = new File("data" + File.separator + "Class_" + details.getStudentClass() + ".xml");
            Document doc = parse.build(dataFile);

            Element root = doc.getRootElement();
            System.out.println("File Path: " + dataFile.getAbsolutePath() + "\nHere: " + root.toXML());

            Element bill = new Element("Bill");
            Attribute billno = new Attribute("no", Utility.details.getBillNo());
            bill.addAttribute(billno);

            Element date = new Element("Date");
            date.appendChild(Utility.details.getDate());

            Element name = new Element("Name");
            name.appendChild(Utility.details.getStudentName());

            Element std = new Element("Standard");
            std.appendChild(Utility.details.getStudentClass());

            Element section = new Element("Section");
            section.appendChild(Utility.details.getStudentSection());

            Element fees = new Element("Fees");
            Element tuition = new Element("Tuition");
            Element stationery = new Element("Stationery");
            Element maintenance = new Element("Maintenance");
            Element transport = new Element("Transport");
            Element total = new Element("Total");
            fees.appendChild(tuition);
            fees.appendChild(stationery);
            fees.appendChild(maintenance);
            fees.appendChild(transport);
            fees.appendChild(total);

            Attribute paid = new Attribute("paid", "" + Utility.details.getPaid(0));
            tuition.addAttribute(paid);
            tuition.appendChild(Utility.details.getTuitionFee());

            paid = new Attribute("paid", "" + Utility.details.getPaid(1));
            stationery.addAttribute(paid);
            stationery.appendChild(Utility.details.getStationeryFee());

            paid = new Attribute("paid", "" + Utility.details.getPaid(2));
            maintenance.addAttribute(paid);
            maintenance.appendChild(Utility.details.getMaintenanceFee());

            paid = new Attribute("paid", "" + Utility.details.getPaid(3));
            transport.addAttribute(paid);
            transport.appendChild(Utility.details.getTransportFee());

            total.appendChild(Utility.details.getTotalFee());

            bill.appendChild(date);
            bill.appendChild(name);
            bill.appendChild(std);
            bill.appendChild(section);
            bill.appendChild(fees);

            root.appendChild(bill);

            System.out.println("After adding new data:\n\n" + root.toXML());

            FileOutputStream fos = new FileOutputStream(dataFile);
            Serializer serialize = new Serializer(fos);
            serialize.setIndent(4);
            serialize.setMaxLength(100);
            serialize.write(doc);
            fos.close();

            // Code to update invoice number seperately after file update complete
            Builder parser = new Builder(false);
            File settingsFile = new File("data" + File.separator + "settings.xml");
            doc = parser.build(settingsFile);

            Element settings = doc.getRootElement();
            Element invoice = (Element) settings.getFirstChildElement("Invoice");
            Element serial = invoice.getFirstChildElement("Last-Used-Serial");

            int no = Integer.parseInt(Utility.invoiceNumber) + 1;
            System.out.println("No: " + no);
            serial.removeChildren();
            serial.appendChild("" + no);

            fos = new FileOutputStream(settingsFile);
            serialize = new Serializer(fos);
            serialize.setIndent(4);
            serialize.setMaxLength(100);
            serialize.write(doc);
            fos.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
