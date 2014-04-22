/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps.threads;

import java.io.File;
import javax.swing.JPanel;

/**
 *
 * @author Vignesh
 */
public class FeeFileStorage implements Runnable {

    private String Invoice, StudentName, Standard, Section;
    private float TotalFees, Tuition, Stationary, Maintenance,Transport;
    private JPanel srcPanel;
    
    public FeeFileStorage() {
    }
    
    public FeeFileStorage(String Invoice, String Name, String Std, String Sec, float Tuition, float Stationary, float Maintenance, float Transport, float TotalFees, JPanel panel) {
        this.Invoice = Invoice;
        StudentName = Name;
        Standard = Std;
        Section = Sec;
        this.Tuition = Tuition;
        this.Stationary = Stationary;
        this.Maintenance = Maintenance;
        this.Transport = Transport;
        this.TotalFees = TotalFees;
        
        srcPanel = panel;
    }
    
    public void writeToFile() {
        File folder = new File("data");
        if(!folder.exists()){
            folder.mkdir();
        }
        File file = new File("data/test.xml");
        if(!file.exists()){
            System.out.println("File does not exist");
        }
    }
    
    public void run(){
        
    }
    
}
