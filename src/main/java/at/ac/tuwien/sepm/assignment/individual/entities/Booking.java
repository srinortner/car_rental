package at.ac.tuwien.sepm.assignment.individual.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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



    public Booking(Long id, String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status) {
        this.id = id;
        this.name = name;
        this.paymentType = paymentType;
        this.paymentNumber = paymentNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookedVehicles = bookedVehicles;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Booking(String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status, LocalDateTime createtime) {
        this.name = name;
        this.paymentType = paymentType;
        this.paymentNumber = paymentNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookedVehicles = bookedVehicles;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createtime = createtime;
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
}
