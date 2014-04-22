/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps.threads;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

/**
 *
 * @author Vignesh
 */
public class tester {

    public static void main(String[] args) {
        try {
            Date date = new Date();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println(sdf.format(date));
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
