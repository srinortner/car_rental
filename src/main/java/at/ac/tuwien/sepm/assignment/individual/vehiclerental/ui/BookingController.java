package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.PaymentType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class BookingController {

    private List<Vehicle> vehicleList = null;
    @FXML
    private ToggleGroup bookingtype;

    @FXML
    private RadioButton IBANButtonBooking;

    @FXML
    private ToggleGroup paymenttype;

    @FXML
    private TextField nameOfPersonBooking;

    @FXML
    private RadioButton creditCardButtonBooking;

    @FXML
    private TextField paymentNumberBooking;

    @FXML
    private DatePicker fromDatePickerBooking;

    @FXML
    private DatePicker toDatePickerBooking;

    @FXML
    private Label vehiclesOfBooking;

    @FXML
    private Label PricesOfBooking;

    @FXML
    private Label totalPriceOfBooking;

    @FXML
    private Button saveButtonBooking;

    @FXML
    private Button backButtonBooking;

    @FXML
    private Button vehiclesButtonBooking;

    @FXML
    private CheckBox CLicenseCheckBox;

    @FXML
    private CheckBox BLicenseCheckBox;

    @FXML
    private CheckBox ALicenseCheckBox;

    @FXML
    private DatePicker ALicenseDateBooking;

    @FXML
    private DatePicker BLicenseDateBooking;

    @FXML
    private DatePicker CLicenseDateBooking;

    @FXML
    private RadioButton paidRadioButton;

    @FXML
    private RadioButton bookedRadioButton;

    @FXML
    private RadioButton canceledRadioButton;

    @FXML
    private void saveBooking(ActionEvent event) {
        String currentName = nameOfPersonBooking.getText();
        PaymentType currentPaymentType = null;
        if(creditCardButtonBooking.isSelected()) {
            currentPaymentType = PaymentType.CREDITCARD;
        } else {
            currentPaymentType = PaymentType.IBAN;
        }
        String currentPaymentNumber = paymentNumberBooking.getText();
        LocalDate currentStartDate = fromDatePickerBooking.getValue();
        LocalDate currentEndDate = toDatePickerBooking.getValue();
        int dailyPrice = 0;
        for (Vehicle vehicle: vehicleList) {
            dailyPrice =vehicle.getHourlyRateCents()*24;
        }
        Integer currentTotalPrice = 0;
        LocalDate i = currentStartDate;
        while (!i.equals(currentEndDate)) {
            currentTotalPrice += dailyPrice;
            i.plusDays(1);
        }
        BookingStatus currentStatus = null;
        if(bookedRadioButton.isSelected()) {
            currentStatus = BookingStatus.BOOKED;
        } else if (paidRadioButton.isSelected()) {
            currentStatus = BookingStatus.PAID;
        } else {
            currentStatus = BookingStatus.CANCELED;
        }
        //String name, PaymentType paymentType, String paymentNumber, LocalDate startDate, LocalDate endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status
        Booking currentBooking = new Booking(currentName,currentPaymentType,currentPaymentNumber,currentStartDate,currentEndDate, vehicleList,currentTotalPrice,currentStatus, LocalDateTime.now());


    }

    @FXML
    private void openBookingTableView(ActionEvent event) {

    }

    @FXML
    private void openVehicleTableView(ActionEvent event) {

    }




}

