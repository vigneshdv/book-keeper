/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps;

import javax.swing.JOptionPane;
import betaps.display.AboutMenuPanel;
import betaps.display.DefaultPanel;
import betaps.display.FeeFormGenPanel;
import com.alee.laf.WebLookAndFeel;
import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

/**
 *
 * @author Vignesh
 */
public class MainWindow extends javax.swing.JFrame {

    private DefaultPanel defaultPanel;
    private FeeFormGenPanel feePanel;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        checkSettingsStatus();
        initComponents();
        layoutAdjustments();

    }

    public void layoutAdjustments() {
        defaultPanel = new DefaultPanel();
        feePanel = new FeeFormGenPanel();

        CardLayout cl = (CardLayout) CardDeck.getLayout();
        CardDeck.add(defaultPanel, "card0");
        CardDeck.add(feePanel, "card1");

        cl.show(CardDeck, "card0");

        System.out.println(CardDeck.getLayout().toString());
    }

    /*
    *   checkSettingsStatus is run on the main GUI event thread simply because    
    *   no GUI is loaded before it and therefore the user will not be aware of
    *   the delay in running this method
    */
    public void checkSettingsStatus() {
        try {
            File folder = new File("data");
            File settings = new File("data" + File.separator + "Settings.xml");
            if (!folder.exists() || !settings.exists()) {
                folder.mkdir();
                String msg = "Settings.xml is missing.\n Press Yes If you are running this program for the first time. Press No if you have already run this program to find the Settings.xml file";
                int val = JOptionPane.showOptionDialog(rootPane, msg, "Settings.xml is missing", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                System.out.println("User selected value: " + val);
                if (val == 0) {
                    setupSettings();
                } else if (val == 1) {
                    copySettings();
                } else {
                    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
                //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setupSettings() {
        try {
            Element settings = new Element("Settings");
            Element invoice = new Element("Invoice");
            Element serial = new Element("Last-Used-Serial");
            Element backup = new Element("Backup");
            Attribute available = new Attribute("available", "false");

            backup.addAttribute(available);
            invoice.appendChild(serial);
            serial.appendChild("0");
            settings.appendChild(invoice);
            settings.appendChild(backup);

            Document SettingsDoc = new Document(settings);
            System.out.println(SettingsDoc.toXML());

            File file = new File("data" + File.separator + "Settings.xml");
            FileOutputStream fos = new FileOutputStream(file);
            Serializer serialize = new Serializer(fos);
            serialize.setIndent(4);
            serialize.setMaxLength(100);
            serialize.write(SettingsDoc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void copySettings() {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Settings.xml");
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            int retValue = fileChooser.showOpenDialog(null);

            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                Builder parser = new Builder(false);
                File settingsFile = new File(file.getAbsolutePath());
                Document backupFile = parser.build(settingsFile);

                File newSettingsFile = new File("data" + File.separator + "Settings.xml");
                FileOutputStream fos = new FileOutputStream(newSettingsFile);
                Serializer serialize = new Serializer(fos);
                serialize.setIndent(4);
                serialize.setMaxLength(100);
                serialize.write(backupFile);

            } else {
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CardDeck = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        File_PrintMenuItem = new javax.swing.JMenuItem();
        File_ExitMenuItem = new javax.swing.JMenuItem();
        EditMenu = new javax.swing.JMenu();
        SearchMenu = new javax.swing.JMenu();
        HelpMenu = new javax.swing.JMenu();
        Help_HelpMenuItem = new javax.swing.JMenuItem();
        Help_AboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Record Keeper");
        setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        CardDeck.setLayout(new java.awt.CardLayout());
        getContentPane().add(CardDeck, java.awt.BorderLayout.CENTER);

        MenuBar.setMaximumSize(new java.awt.Dimension(126, 21));
        MenuBar.setMinimumSize(new java.awt.Dimension(126, 21));

        FileMenu.setText("File");
        FileMenu.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        File_PrintMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        File_PrintMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        File_PrintMenuItem.setText("Generate Invoice");
        File_PrintMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                File_PrintMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(File_PrintMenuItem);

        File_ExitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        File_ExitMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        File_ExitMenuItem.setText("Exit");
        FileMenu.add(File_ExitMenuItem);

        MenuBar.add(FileMenu);

        EditMenu.setText("Edit");
        EditMenu.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        MenuBar.add(EditMenu);

        SearchMenu.setText("Search");
        SearchMenu.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        MenuBar.add(SearchMenu);

        HelpMenu.setText("Help");
        HelpMenu.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        Help_HelpMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        Help_HelpMenuItem.setText("Help");
        HelpMenu.add(Help_HelpMenuItem);

        Help_AboutMenuItem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        Help_AboutMenuItem.setText("About");
        Help_AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Help_AboutMenuItemActionPerformed(evt);
            }
        });
        HelpMenu.add(Help_AboutMenuItem);

        MenuBar.add(HelpMenu);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Help_AboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Help_AboutMenuItemActionPerformed
        AboutMenuPanel amp = new AboutMenuPanel();
        JOptionPane.showMessageDialog(rootPane, amp, "", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_Help_AboutMenuItemActionPerformed

    private void File_PrintMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_File_PrintMenuItemActionPerformed
        CardLayout cl = (CardLayout) CardDeck.getLayout();
        cl.show(CardDeck, "card1");
        this.repaint();
    }//GEN-LAST:event_File_PrintMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardDeck;
    private javax.swing.JMenu EditMenu;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem File_ExitMenuItem;
    private javax.swing.JMenuItem File_PrintMenuItem;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenuItem Help_AboutMenuItem;
    private javax.swing.JMenuItem Help_HelpMenuItem;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu SearchMenu;
    // End of variables declaration//GEN-END:variables
}
