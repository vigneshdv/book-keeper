/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps.display;

import betaps.model.StudentFee;
import betaps.threads.Utility;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

public class FeeFormGenPanel extends javax.swing.JPanel {

    private int NoOfClasses = 9;
    private Pattern NumericPattern;

    public FeeFormGenPanel() {

        this.NumericPattern = Pattern.compile("^([0-9]+).?([0-9])*$");
        initComponents();

    }

    public void createNewDataFiles() {
        try {
            File dataFiles[] = new File[9];
            String name = "data" + File.separator + "Class_";

            for (int i = 0; i < NoOfClasses; i++) {
                dataFiles[i] = new File(name + StandardCB.getItemAt(i) + ".xml");
                //System.out.println(dataFiles[i].getCanonicalPath());
            }

            Element invoice = new Element("Root");
            Document doc = new Document(invoice);
            Serializer serialize;
            FileOutputStream fos;

            for (int i = 0; i < NoOfClasses; i++) {

                fos = new FileOutputStream(dataFiles[i]);

                serialize = new Serializer(fos);
                serialize.setIndent(4);
                serialize.setMaxLength(100);
                serialize.write(doc);

                fos.close();
                dataFiles[i] = null;
                serialize = null;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void copyDataFiles(boolean flag) {

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Select directory containing the data files");
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            int retVal = fileChooser.showOpenDialog(null);

            if (retVal == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().toString();

                //System.out.println(path.toString());
                Builder parser = new Builder(false);
                File srcFile;
                Serializer serialize;

                for (int i = 0; i < NoOfClasses; i++) {

                    srcFile = new File(path + File.separator + "Class_" + StandardCB.getItemAt(i) + ".xml");
                    Document doc = parser.build(srcFile);

                    File destFile = new File("data" + File.separator + "Class_" + StandardCB.getItemAt(i) + ".xml");

                    FileOutputStream fos = new FileOutputStream(destFile);
                    serialize = new Serializer(fos);
                    serialize.setIndent(4);
                    serialize.setMaxLength(100);
                    serialize.write(doc);

                    fos.close();
                    srcFile = destFile = null;
                    serialize = null;

                }

            } else {
                if (flag == false) {
                    flag = true;
                    JOptionPane.showMessageDialog(this, "You must select the folder with the data files");
                    copyDataFiles(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Application cannot proceed without data files.\nApplication will now close");
                    System.exit(0);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void checkForDatabase() {
        try {
            File folder = new File("data");
            File[] list = folder.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    String[] names = {"LKG", "UKG", "I", "II", "III", "IV", "V", "VI", "VII", "VIII"};
                    int flag = 0;
                    for (String name : names) {
                        if (pathname.getName().endsWith(name + ".xml")) {
                            System.out.println(pathname.getName());
                            return true;
                        }
                    }
                    return false;
                }
            });

            System.out.println("Length: " + list.length);

            if (list.length != 9) {

                String msg = "Student Database Files are missing. \nPress Yes if you are running this application for the first time. "
                        + "\nPress No if you wish to backup the data files from another location.";
                int val = JOptionPane.showOptionDialog(this, msg, "Settings.xml is missing", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                if (val == JOptionPane.YES_OPTION) {

                    createNewDataFiles();

                } else if (val == JOptionPane.NO_OPTION) {
                    copyDataFiles(false);

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void readInvoiceNumberFromFile() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Utility.readandsetInvoiceNumber();
                setInvoiceNumberInDisplay(Utility.invoiceNumber);
            }
        });
    }

    public void setInvoiceNumberInDisplay(String tNo) {

        int val = Integer.parseInt(tNo) + 1;
        this.InvoiceNumberTF.setText(String.format("%04d", val));
    }

    public boolean checkIfNumber(String temp) {

        Pattern.matches("^(([0-9]*)|(([0-9]*).([0-9]*)))$", temp);

        return true;
    }

    public void updateFeeTotal() {
        float TuitionFee = this.TuitionFeeTF.getText().equalsIgnoreCase("") ? 0 : Float.parseFloat(this.TuitionFeeTF.getText());
        float StationeryFee = this.StationeryFeeTF.getText().equalsIgnoreCase("") ? 0 : Float.parseFloat(this.StationeryFeeTF.getText());
        float MaintenanceFee = this.MaintenanceFeeTF.getText().equalsIgnoreCase("") ? 0 : Float.parseFloat(this.MaintenanceFeeTF.getText());
        float TransportFee = this.TransportFeeTF.getText().equalsIgnoreCase("") ? 0 : Float.parseFloat(this.TransportFeeTF.getText());

        float total = TuitionFee + StationeryFee + MaintenanceFee + TransportFee;

        this.TotalFeeTF.setText(Float.toString(total));
    }

    public boolean checkDataFields() {
        System.out.println("Called here");
        if (this.StudentNameTF.getText().equalsIgnoreCase("")) {
            System.out.println("From Name " + this.StudentNameTF.getText().equals(""));
            return false;
        } else if (this.TotalFeeTF.getText().equalsIgnoreCase("")) {
            System.out.println("From Total " + this.TotalFeeTF.getText().equals(""));
            return false;
        }

        return true;
    }

    public void saveDataToFile() {
        try {
            String name = this.StudentNameTF.getText();
            String stdclass = this.SectionCB.getSelectedItem().toString();
            String stdsec = this.StandardCB.getSelectedItem().toString();
            String tuition = this.TuitionFeeTF.getText().equalsIgnoreCase("") ? "not paid" : this.TuitionFeeTF.getText();
            String stationery = this.StationeryFeeTF.getText().equalsIgnoreCase("") ? "not paid" : this.StationeryFeeTF.getText();
            String maintenance = this.MaintenanceFeeTF.getText().equalsIgnoreCase("") ? "not paid" : this.MaintenanceFeeTF.getText();
            String transport = this.TransportFeeTF.getText().equalsIgnoreCase("") ? "not paid" : this.TransportFeeTF.getText();
            String total = this.TotalFeeTF.getText();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(d);
            
            boolean[] paid = new boolean[4];
            paid[0] = this.TuitionFeeTF.getText().equalsIgnoreCase("") ? false : true;
            paid[1] = this.StationeryFeeTF.getText().equalsIgnoreCase("") ? false : true;
            paid[2] = this.MaintenanceFeeTF.getText().equalsIgnoreCase("") ? false : true;
            paid[3] = this.TransportFeeTF.getText().equalsIgnoreCase("") ? false : true;
            
            StudentFee details = new StudentFee(name, stdclass, stdsec, tuition, stationery, maintenance, transport, total, date, paid);
            Utility.details = details;
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    Utility.writeInvoiceToFile();
                }
            
            
            });
            
    
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

        StudentDetailsPanel = new javax.swing.JPanel();
        StudentNameLabel = new javax.swing.JLabel();
        StudentNameTF = new javax.swing.JTextField();
        StandardLabel = new javax.swing.JLabel();
        SectionCB = new javax.swing.JComboBox();
        SectionLabel = new javax.swing.JLabel();
        StandardCB = new javax.swing.JComboBox();
        InvoiceNumber = new javax.swing.JLabel();
        InvoiceNumberTF = new javax.swing.JTextField();
        FeeParticularsPanel = new javax.swing.JPanel();
        TuitionFeeLabel = new javax.swing.JLabel();
        TuitionFeeTF = new javax.swing.JTextField();
        StationeryFeeLabel = new javax.swing.JLabel();
        StationeryFeeTF = new javax.swing.JTextField();
        MaintenanceFeeLabel = new javax.swing.JLabel();
        MaintenanceFeeTF = new javax.swing.JTextField();
        TransportFeeLabel = new javax.swing.JLabel();
        TransportFeeTF = new javax.swing.JTextField();
        TotalFeeTF = new javax.swing.JTextField();
        TotalFeeLabel = new javax.swing.JLabel();
        UpdateButton = new javax.swing.JButton();
        PrintButton = new javax.swing.JButton();

        setAutoscrolls(true);
        setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        setMaximumSize(new java.awt.Dimension(800, 549));
        setMinimumSize(new java.awt.Dimension(800, 549));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 549));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(44, 62, 80)), "Student Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 2, 14), new java.awt.Color(113, 128, 135))); // NOI18N
        StudentDetailsPanel.setPreferredSize(new java.awt.Dimension(700, 100));
        StudentDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentNameLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StudentNameLabel.setForeground(new java.awt.Color(33, 36, 37));
        StudentNameLabel.setText("Student Name");
        StudentDetailsPanel.add(StudentNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        StudentNameTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StudentNameTF.setForeground(new java.awt.Color(33, 36, 37));
        StudentNameTF.setToolTipText("Enter the name of the student");
        StudentNameTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        StudentNameTF.setMaximumSize(new java.awt.Dimension(300, 22));
        StudentNameTF.setMinimumSize(new java.awt.Dimension(100, 22));
        StudentNameTF.setPreferredSize(new java.awt.Dimension(200, 22));
        StudentDetailsPanel.add(StudentNameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        StandardLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StandardLabel.setForeground(new java.awt.Color(33, 36, 37));
        StandardLabel.setLabelFor(SectionCB);
        StandardLabel.setText("Standard");
        StudentDetailsPanel.add(StandardLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        SectionCB.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        SectionCB.setForeground(new java.awt.Color(33, 36, 37));
        SectionCB.setMaximumRowCount(5);
        SectionCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C", "D", "E", "F" }));
        SectionCB.setToolTipText("Select the standard the student is studying");
        StudentDetailsPanel.add(SectionCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 40, -1));

        SectionLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        SectionLabel.setForeground(new java.awt.Color(33, 36, 37));
        SectionLabel.setText("Section");
        StudentDetailsPanel.add(SectionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, -1));

        StandardCB.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StandardCB.setForeground(new java.awt.Color(33, 36, 37));
        StandardCB.setMaximumRowCount(14);
        StandardCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LKG", "UKG", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" }));
        StandardCB.setToolTipText("Select the standard the student is studying");
        StudentDetailsPanel.add(StandardCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 60, -1));

        add(StudentDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 700, 100));

        InvoiceNumber.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        InvoiceNumber.setForeground(new java.awt.Color(113, 128, 135));
        InvoiceNumber.setLabelFor(InvoiceNumberTF);
        InvoiceNumber.setText("Invoice Number: ");
        InvoiceNumber.setEnabled(false);
        add(InvoiceNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 100, 20));

        InvoiceNumberTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        InvoiceNumberTF.setForeground(new java.awt.Color(33, 36, 37));
        InvoiceNumberTF.setToolTipText("Enter the name of the student");
        InvoiceNumberTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        InvoiceNumberTF.setEnabled(false);
        InvoiceNumberTF.setMaximumSize(new java.awt.Dimension(300, 22));
        InvoiceNumberTF.setMinimumSize(new java.awt.Dimension(100, 22));
        InvoiceNumberTF.setPreferredSize(new java.awt.Dimension(200, 22));
        add(InvoiceNumberTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 60, -1));

        FeeParticularsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(44, 62, 80)), "Fee Particulars", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 2, 14), new java.awt.Color(113, 128, 135))); // NOI18N
        FeeParticularsPanel.setMaximumSize(new java.awt.Dimension(700, 300));
        FeeParticularsPanel.setMinimumSize(new java.awt.Dimension(700, 300));
        FeeParticularsPanel.setPreferredSize(new java.awt.Dimension(700, 300));
        FeeParticularsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TuitionFeeLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        TuitionFeeLabel.setForeground(new java.awt.Color(33, 36, 37));
        TuitionFeeLabel.setLabelFor(TuitionFeeTF);
        TuitionFeeLabel.setText("Tuition Fee");
        FeeParticularsPanel.add(TuitionFeeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        TuitionFeeTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        TuitionFeeTF.setForeground(new java.awt.Color(33, 36, 37));
        TuitionFeeTF.setToolTipText("Enter tuition fee");
        TuitionFeeTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TuitionFeeTF.setMaximumSize(new java.awt.Dimension(300, 22));
        TuitionFeeTF.setMinimumSize(new java.awt.Dimension(100, 22));
        TuitionFeeTF.setPreferredSize(new java.awt.Dimension(200, 22));
        TuitionFeeTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TuitionFeeTFCaretUpdate(evt);
            }
        });
        FeeParticularsPanel.add(TuitionFeeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        StationeryFeeLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StationeryFeeLabel.setForeground(new java.awt.Color(33, 36, 37));
        StationeryFeeLabel.setLabelFor(StationeryFeeTF);
        StationeryFeeLabel.setText("Books, Notebooks & Other Charges");
        FeeParticularsPanel.add(StationeryFeeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        StationeryFeeTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        StationeryFeeTF.setForeground(new java.awt.Color(33, 36, 37));
        StationeryFeeTF.setToolTipText("Enter cost for books, notebooks, etc");
        StationeryFeeTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        StationeryFeeTF.setMaximumSize(new java.awt.Dimension(300, 22));
        StationeryFeeTF.setMinimumSize(new java.awt.Dimension(100, 22));
        StationeryFeeTF.setPreferredSize(new java.awt.Dimension(200, 22));
        StationeryFeeTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                StationeryFeeTFCaretUpdate(evt);
            }
        });
        FeeParticularsPanel.add(StationeryFeeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        MaintenanceFeeLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        MaintenanceFeeLabel.setForeground(new java.awt.Color(33, 36, 37));
        MaintenanceFeeLabel.setLabelFor(MaintenanceFeeTF);
        MaintenanceFeeLabel.setText("School Maintenace & Other Charges");
        FeeParticularsPanel.add(MaintenanceFeeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        MaintenanceFeeTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        MaintenanceFeeTF.setForeground(new java.awt.Color(33, 36, 37));
        MaintenanceFeeTF.setToolTipText("Enter maintenance charges, etc");
        MaintenanceFeeTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        MaintenanceFeeTF.setMaximumSize(new java.awt.Dimension(300, 22));
        MaintenanceFeeTF.setMinimumSize(new java.awt.Dimension(100, 22));
        MaintenanceFeeTF.setPreferredSize(new java.awt.Dimension(200, 22));
        MaintenanceFeeTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                MaintenanceFeeTFCaretUpdate(evt);
            }
        });
        FeeParticularsPanel.add(MaintenanceFeeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        TransportFeeLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        TransportFeeLabel.setForeground(new java.awt.Color(33, 36, 37));
        TransportFeeLabel.setLabelFor(TransportFeeTF);
        TransportFeeLabel.setText("Transport Fee");
        FeeParticularsPanel.add(TransportFeeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        TransportFeeTF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        TransportFeeTF.setForeground(new java.awt.Color(33, 36, 37));
        TransportFeeTF.setToolTipText("Enter transportation charges");
        TransportFeeTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TransportFeeTF.setMaximumSize(new java.awt.Dimension(300, 22));
        TransportFeeTF.setMinimumSize(new java.awt.Dimension(100, 22));
        TransportFeeTF.setPreferredSize(new java.awt.Dimension(200, 22));
        TransportFeeTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TransportFeeTFCaretUpdate(evt);
            }
        });
        FeeParticularsPanel.add(TransportFeeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        TotalFeeTF.setFont(new java.awt.Font("Calibri", 0, 28)); // NOI18N
        TotalFeeTF.setForeground(new java.awt.Color(33, 36, 37));
        TotalFeeTF.setToolTipText("");
        TotalFeeTF.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TotalFeeTF.setEnabled(false);
        TotalFeeTF.setMaximumSize(new java.awt.Dimension(300, 22));
        TotalFeeTF.setMinimumSize(new java.awt.Dimension(100, 22));
        TotalFeeTF.setPreferredSize(new java.awt.Dimension(200, 22));
        FeeParticularsPanel.add(TotalFeeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, 40));

        TotalFeeLabel.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        TotalFeeLabel.setForeground(new java.awt.Color(33, 36, 37));
        TotalFeeLabel.setLabelFor(TuitionFeeTF);
        TotalFeeLabel.setText("Total Amount");
        FeeParticularsPanel.add(TotalFeeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, -1, -1));

        add(FeeParticularsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 700, 300));

        UpdateButton.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        UpdateButton.setForeground(new java.awt.Color(33, 36, 37));
        UpdateButton.setText("Update Records");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });
        add(UpdateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 160, 40));

        PrintButton.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        PrintButton.setForeground(new java.awt.Color(33, 36, 37));
        PrintButton.setText("Print Invoice");
        add(PrintButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, 160, 40));

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void TuitionFeeTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TuitionFeeTFCaretUpdate
        if (this.NumericPattern.asPredicate().test(this.TuitionFeeTF.getText())) {
            updateFeeTotal();
        }

    }//GEN-LAST:event_TuitionFeeTFCaretUpdate

    private void StationeryFeeTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_StationeryFeeTFCaretUpdate
        if (this.NumericPattern.asPredicate().test(this.StationeryFeeTF.getText())) {
            updateFeeTotal();
        }
    }//GEN-LAST:event_StationeryFeeTFCaretUpdate

    private void MaintenanceFeeTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_MaintenanceFeeTFCaretUpdate
        if (this.NumericPattern.asPredicate().test(this.MaintenanceFeeTF.getText())) {
            updateFeeTotal();
        }
    }//GEN-LAST:event_MaintenanceFeeTFCaretUpdate

    private void TransportFeeTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TransportFeeTFCaretUpdate
        if (this.NumericPattern.asPredicate().test(this.TransportFeeTF.getText())) {
            updateFeeTotal();
        }
    }//GEN-LAST:event_TransportFeeTFCaretUpdate

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        if (checkDataFields()) {
            saveDataToFile();
        } else {
            JOptionPane.showMessageDialog(this, "Some Fields are empty. Please fill them appropriately and try again.", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        readInvoiceNumberFromFile();
        checkForDatabase();
    }//GEN-LAST:event_formComponentShown

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FeeParticularsPanel;
    private javax.swing.JLabel InvoiceNumber;
    private javax.swing.JTextField InvoiceNumberTF;
    private javax.swing.JLabel MaintenanceFeeLabel;
    private javax.swing.JTextField MaintenanceFeeTF;
    private javax.swing.JButton PrintButton;
    private javax.swing.JComboBox SectionCB;
    private javax.swing.JLabel SectionLabel;
    private javax.swing.JComboBox StandardCB;
    private javax.swing.JLabel StandardLabel;
    private javax.swing.JLabel StationeryFeeLabel;
    private javax.swing.JTextField StationeryFeeTF;
    private javax.swing.JPanel StudentDetailsPanel;
    private javax.swing.JLabel StudentNameLabel;
    private javax.swing.JTextField StudentNameTF;
    private javax.swing.JLabel TotalFeeLabel;
    private javax.swing.JTextField TotalFeeTF;
    private javax.swing.JLabel TransportFeeLabel;
    private javax.swing.JTextField TransportFeeTF;
    private javax.swing.JLabel TuitionFeeLabel;
    private javax.swing.JTextField TuitionFeeTF;
    private javax.swing.JButton UpdateButton;
    // End of variables declaration//GEN-END:variables
}
