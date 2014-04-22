/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps.display;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Vignesh
 */
public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel deck = new JPanel();
        
        deck.setLayout(new CardLayout());
        CardLayout cl = (CardLayout)deck.getLayout();
        
        
        deck.add(new DefaultPanel(), "dp");
        deck.add(new FeeFormGenPanel(), "fp");
        frame.add(deck,BorderLayout.CENTER);
        frame.setSize(800,600);
        frame.setVisible(true);
        
        
    }
    
}
