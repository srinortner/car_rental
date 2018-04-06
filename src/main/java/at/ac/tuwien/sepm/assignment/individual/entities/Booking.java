package at.ac.tuwien.sepm.assignment.individual.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Booking {

    private Long id;
    private String name;
    private PaymentType paymentType;
    private String paymentNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Vehicle> bookedVehicles;
    private Integer totalPrice;
    private BookingStatus status;
    private LocalDateTime createtime;
    private LocalDate licensedateA;
    private LocalDate licensedateB;
    private LocalDate licensedateC;
    private String licensenumberA;
    private String licensenumberB;
    private String licensenumberC;
    private List<LicenseType> personLicenseList;
    private String vehicleString;
    private Timestamp paidtime;
    private int cancelingFeeInPercent = 0;
    private Integer invoiceNumber = 0;



    public Booking(Long id, String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, String bookedVehicles, Integer totalPrice, BookingStatus status, Timestamp createtime, Timestamp paidtime) {
        this.id = id;
        this.name = name;
        this.paymentType = paymentType;
        this.paymentNumber = paymentNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.vehicleString = bookedVehicles;
        this.status = status;
        this.createtime = createtime.toLocalDateTime();
        this.paidtime = paidtime;
    }

    public Booking(String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status, LocalDateTime createtime ) {
        this.name = name;
        this.paymentType = paymentType;
        this.paymentNumber = paymentNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookedVehicles = bookedVehicles;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createtime = createtime;
        this.paidtime = null;

    }

    public Booking(Long id, LocalDateTime startDate, LocalDateTime endDate, Integer totalPrice) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Vehicle> getBookedVehicles() {
        return bookedVehicles;
    }

    public void setBookedVehicles(List<Vehicle> bookedVehicles) {
        this.bookedVehicles = bookedVehicles;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDate getLicensedateA() {
        return licensedateA;
    }

    public void setLicensedateA(LocalDate licensedateA) {
        this.licensedateA = licensedateA;
    }

    public LocalDate getLicensedateB() {
        return licensedateB;
    }

    public void setLicensedateB(LocalDate licensedateB) {
        this.licensedateB = licensedateB;
    }

    public LocalDate getLicensedateC() {
        return licensedateC;
    }

    public void setLicensedateC(LocalDate licensedateC) {
        this.licensedateC = licensedateC;
    }

    public List<LicenseType> getPersonLicenseList() {
        return personLicenseList;
    }

    public void setPersonLicenseList(List<LicenseType> personLicenseList) {
        this.personLicenseList = personLicenseList;
    }

    public String getLicensenumberA() {
        return licensenumberA;
    }

    public void setLicensenumberA(String licensenumberA) {
        this.licensenumberA = licensenumberA;
    }

    public String getLicensenumberB() {
        return licensenumberB;
    }

    public void setLicensenumberB(String licensenumberB) {
        this.licensenumberB = licensenumberB;
    }

    public String getLicensenumberC() {
        return licensenumberC;
    }

    public void setLicensenumberC(String licensenumberC) {
        this.licensenumberC = licensenumberC;
    }

    public Timestamp getPaidtime() {
        return paidtime;
    }

    public void setPaidtime(Timestamp paidtime) {
        this.paidtime = paidtime;
    }

    public int getCancelingFeeInPercent() {
        return cancelingFeeInPercent;
    }

    public void setCancelingFeeInPercent(int cancelingFeeInPercent) {
        this.cancelingFeeInPercent = cancelingFeeInPercent;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return cancelingFeeInPercent == booking.cancelingFeeInPercent &&
            Objects.equals(id, booking.id) &&
            Objects.equals(name, booking.name) &&
            paymentType == booking.paymentType &&
            Objects.equals(paymentNumber, booking.paymentNumber) &&
            Objects.equals(startDate, booking.startDate) &&
            Objects.equals(endDate, booking.endDate) &&
            Objects.equals(bookedVehicles, booking.bookedVehicles) &&
            Objects.equals(totalPrice, booking.totalPrice) &&
            status == booking.status &&
            Objects.equals(createtime, booking.createtime) &&
            Objects.equals(licensedateA, booking.licensedateA) &&
            Objects.equals(licensedateB, booking.licensedateB) &&
            Objects.equals(licensedateC, booking.licensedateC) &&
            Objects.equals(licensenumberA, booking.licensenumberA) &&
            Objects.equals(licensenumberB, booking.licensenumberB) &&
            Objects.equals(licensenumberC, booking.licensenumberC) &&
            Objects.equals(personLicenseList, booking.personLicenseList) &&
            Objects.equals(vehicleString, booking.vehicleString) &&
            Objects.equals(paidtime, booking.paidtime) &&
            Objects.equals(invoiceNumber, booking.invoiceNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, paymentType, paymentNumber, startDate, endDate, bookedVehicles, totalPrice, status, createtime, licensedateA, licensedateB, licensedateC, licensenumberA, licensenumberB, licensenumberC, personLicenseList, vehicleString, paidtime, cancelingFeeInPercent, invoiceNumber);
    }
}
