/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betaps.model;

/**
 *
 * @author Vignesh
 */
public class StudentFee {

    private String StudentName;
    private String StudentClass;
    private String StudentSection;
    private String TuitionFee;
    private String StationeryFee;
    private String MaintenanceFee;
    private String TransportFee;
    private String TotalFee;
    private String Date;
    private String BillNo;
    private boolean[] Paid;

    public StudentFee() {
    }

    public StudentFee(String StudentName, String StudentClass,
            String StudentSection, String TuitionFee,
            String StationeryFee, String MaintenanceFee,
            String TransportFee, String TotalFee,
            String Date, String BillNo, boolean[] Paid) {

        this.StudentName = StudentName;
        this.StudentClass = StudentClass;
        this.StudentSection = StudentSection;
        this.TuitionFee = TuitionFee;
        this.StationeryFee = StationeryFee;
        this.MaintenanceFee = MaintenanceFee;
        this.TransportFee = TransportFee;
        this.TotalFee = TotalFee;
        this.Date = Date;
        this.BillNo = BillNo;
        this.Paid = Paid;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public String getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(String StudentClass) {
        this.StudentClass = StudentClass;
    }

    public String getStudentSection() {
        return StudentSection;
    }

    public void setStudentSection(String StudentSection) {
        this.StudentSection = StudentSection;
    }

    public String getTuitionFee() {
        return TuitionFee;
    }

    public void setTuitionFee(String TuitionFee) {
        this.TuitionFee = TuitionFee;
    }

    public String getStationeryFee() {
        return StationeryFee;
    }

    public void setStationeryFee(String StationeryFee) {
        this.StationeryFee = StationeryFee;
    }

    public String getMaintenanceFee() {
        return MaintenanceFee;
    }

    public void setMaintenanceFee(String MaintenanceFee) {
        this.MaintenanceFee = MaintenanceFee;
    }

    public String getTransportFee() {
        return TransportFee;
    }

    public void setTransportFee(String TransportFee) {
        this.TransportFee = TransportFee;
    }

    public String getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(String TotalFee) {
        this.TotalFee = TotalFee;
    }

    public boolean getPaid(int index) {
        return Paid[index];
    }

    public void setPaid(int index, boolean value) {
        this.Paid[index] = value;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String BillNo) {
        this.BillNo = BillNo;
    }
    
    
}
