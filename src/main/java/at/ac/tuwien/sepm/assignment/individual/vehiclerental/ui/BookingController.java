package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.OK;

public class BookingController {

    private List<Vehicle> vehicleList = null;
    private BookingService currentService;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private TableViewController tableViewController;
    private BookingTableViewController bookingTableViewController;
    private Stage primaryStage;

    public TableViewController getTableViewController() {
        return tableViewController;
    }

    public void setTableViewController(TableViewController tableViewController) {
        this.tableViewController = tableViewController;

    }

    public BookingController(BookingService currentService, BookingTableViewController bookingTableViewController, Stage primaryStage) {
        this.currentService = currentService;
        this.bookingTableViewController = bookingTableViewController;
        this.primaryStage = primaryStage;
        bookingTableViewController.setBookingController(this);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

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
    private Label pricesOfBooking;

    @FXML
    private Label totalPriceOfBooking;

    @FXML
    private Label createTimeLabelBooking;

    @FXML
    private Button saveButtonBooking;

    @FXML
    private Button backButtonBooking;

    @FXML
    private Button bookingsButtonBooking;

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
    private TextField licenseNumberA;

    @FXML
    private TextField licenseNumberB;

    @FXML
    private TextField licenseNumberC;

    @FXML
    private Spinner<Integer> fromHourPicker;

    @FXML
    private Spinner<Integer> fromMinutePicker;

    @FXML
    private Spinner<Integer> toHourPicker;

    @FXML
    private Spinner<Integer> toMinutePicker;

    @FXML
    private RadioButton paidRadioButton;

    @FXML
    private RadioButton bookedRadioButton;

    @FXML
    private RadioButton canceledRadioButton;

    @FXML
    private TableView<?> tableViewBookedVehicles;

    @FXML
    private TableColumn<?, ?> vehicleNameColumn;

    @FXML
    private TableColumn<?, ?> vehicleBuildyearColumn;

    @FXML
    private TableColumn<?, ?> VehicleLicenseplateColumn;

    @FXML
    private TableColumn<?, ?> VehiclePriceColumn;

    @FXML
    private Button editButtonBookings;


    private Booking currentBooking = null;

    private LocalDateTime currentStartTime = null;
    private LocalDateTime currentEndTime = null;

    @FXML
    private Label hourlyPricesLabel;

    @FXML
    private Label vehiclesLabel;


    @FXML
    private void initialize() {
        vehiclesOfBooking.setText(vehicleList.toString());
        List<Integer> pricesPerVehicle = new LinkedList<>();
        for (Vehicle vehicle: vehicleList) {
            pricesPerVehicle.add(vehicle.getHourlyRateCents());
        }
        pricesOfBooking.setText(pricesPerVehicle.toString());
        toDatePickerBooking.setValue(LocalDate.now());
        fromDatePickerBooking.setValue(LocalDate.now());


        SpinnerValueFactory<Integer> valueFactoryHour =
            new SpinnerValueFactory<Integer>() {

                @Override
                public void decrement(int steps) {
                    Integer current = this.getValue();
                    if (current == 1) {
                        this.setValue(24);
                    } else {
                        this.setValue(current - 1);
                    }
                }

                @Override
                public void increment(int steps) {
                    Integer current = this.getValue();
                    if (current == 24) {
                        this.setValue(1);
                    } else {
                        this.setValue(current + 1);
                    }
                }
            };

        SpinnerValueFactory<Integer> valueFactoryHourTo =
            new SpinnerValueFactory<Integer>() {

                @Override
                public void decrement(int steps) {
                    Integer current = this.getValue();
                    if (current == 1) {
                        this.setValue(24);
                    } else {
                        this.setValue(current - 1);
                    }
                }

                @Override
                public void increment(int steps) {
                    Integer current = this.getValue();
                    if (current == 24) {
                        this.setValue(1);
                    } else {
                        this.setValue(current + 1);
                    }
                }
            };

        valueFactoryHour.setValue(1);
        valueFactoryHourTo.setValue(1);
        fromHourPicker.setValueFactory(valueFactoryHour);
        toHourPicker.setValueFactory(valueFactoryHourTo);

        SpinnerValueFactory<Integer> valueFactoryMin =
            new SpinnerValueFactory<Integer>() {

                @Override
                public void decrement(int steps) {
                    Integer current = this.getValue();
                    if (current == 0) {
                        this.setValue(59);
                    } else {
                        this.setValue(current - 1);
                    }
                }

                @Override
                public void increment(int steps) {
                    Integer current = this.getValue();
                    if (current == 59) {
                        this.setValue(0);
                    } else {
                        this.setValue(current + 1);
                    }
                }
            };

        SpinnerValueFactory<Integer> valueFactoryMinTo = new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                Integer current = this.getValue();
                if (current == 0) {
                    this.setValue(59);
                } else {
                    this.setValue(current - 1);
                }
            }

            @Override
            public void increment(int steps) {
                Integer current = this.getValue();
                if (current == 59) {
                    this.setValue(0);
                } else {
                    this.setValue(current + 1);
                }
            }
        };

        valueFactoryMin.setValue(0);
        valueFactoryMinTo.setValue(0);
        fromMinutePicker.setValueFactory(valueFactoryMin);
        toMinutePicker.setValueFactory(valueFactoryMinTo);

    }



    @FXML
    private void saveBooking(ActionEvent event) {
        String currentName = nameOfPersonBooking.getText();
        PaymentType currentPaymentType = null;
        if (creditCardButtonBooking.isSelected()) {
            currentPaymentType = PaymentType.CREDITCARD;
        } else {
            currentPaymentType = PaymentType.IBAN;
        }
        String currentPaymentNumber = paymentNumberBooking.getText();


            LocalDate fromDate = fromDatePickerBooking.getValue();
            LocalTime fromTime = LocalTime.of(fromHourPicker.getValue(), fromMinutePicker.getValue());
            currentStartTime = LocalDateTime.of(fromDate, fromTime);




            LocalDate toDate = toDatePickerBooking.getValue();
            LocalTime toTime = LocalTime.of(toHourPicker.getValue(), toMinutePicker.getValue());
            currentEndTime = LocalDateTime.of(toDate, toTime);

            boolean vehiclesAvailable = true;
        int dailyPrice = 0;
        for (Vehicle vehicle : vehicleList) {
            dailyPrice += vehicle.getHourlyRateCents() * 24;
            if(!checkAvailiabilityOfVehicle(vehicle.getId())){
                vehiclesAvailable = false;
            }
        }

        int pricePerMinute = (dailyPrice / 24) / 60;
        Integer currentTotalPrice = 0;
        LocalDateTime i = currentStartTime;

            while (!i.equals(currentEndTime)) {
                currentTotalPrice += pricePerMinute;
                i = i.plusMinutes(1);

        }


        BookingStatus currentStatus = null;
        if (bookedRadioButton.isSelected()) {
            currentStatus = BookingStatus.BOOKED;
        } else if (paidRadioButton.isSelected()) {
            currentStatus = BookingStatus.PAID;
        } else {
            currentStatus = BookingStatus.CANCELED;
        }

        List<LicenseType> currentPersonLicenseList = new LinkedList<>();
        currentBooking = new Booking(currentName, currentPaymentType, currentPaymentNumber, currentStartTime, currentEndTime, vehicleList, currentTotalPrice, currentStatus, LocalDateTime.now());
        if(ALicenseCheckBox.isSelected()){
            currentPersonLicenseList.add(LicenseType.A);
            currentBooking.setLicensedateA(ALicenseDateBooking.getValue());
            currentBooking.setLicensenumberA(licenseNumberA.getText());
        }
        if(BLicenseCheckBox.isSelected()){
            currentPersonLicenseList.add(LicenseType.B);
            currentBooking.setLicensedateB(BLicenseDateBooking.getValue());
            currentBooking.setLicensenumberB(licenseNumberB.getText());
        }
        if(CLicenseCheckBox.isSelected()){
            currentPersonLicenseList.add(LicenseType.C);
            currentBooking.setLicensedateC(CLicenseDateBooking.getValue());
            currentBooking.setLicensenumberC(licenseNumberC.getText());
        }
        currentBooking.setPersonLicenseList(currentPersonLicenseList);

        if(vehiclesAvailable) {
            if (!cancelingPossible()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Free canceling will not be possible for this booking!");
                alert.setContentText("Do you want to continue the booking anyways?");

                if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    //String name, PaymentType paymentType, String paymentNumber, LocalDate startDate, LocalDate endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status

                    try {
                        currentService.addBookingToPersistence(currentBooking);
                    } catch (InvalidBookingException e) {
                        LOG.error("Booking couldn't be added to Persistence! {}", e.getMessage());
                        new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
                    }
                } else {
                    alert.close();
                    returnToVehicleTableView();
                }
            } else {

                try {
                    currentService.addBookingToPersistence(currentBooking);
                    saveLicenseInformation();
                } catch (InvalidBookingException e) {
                    LOG.error("Booking couldn't be added to Persistence! {}", e.getMessage());
                    new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
                }
                returnToVehicleTableView();
            }
        } else {
            new Alert(ERROR, "One of the vehicles is already booked during that time, please select another time!", OK).showAndWait();
        }

    }

    private boolean cancelingPossible() {
        if(paidRadioButton.isSelected()) {
            return false;
        }
        if(LocalDateTime.now().plusWeeks(1).isAfter(currentStartTime)) {
            return false;
        }
        return true;
    }

    private void saveLicenseInformation() {
        for (Vehicle vehicle: vehicleList) {
            if(ALicenseCheckBox.isSelected()) {
                currentService.addLicenseInformationToPersistence(vehicle.getId(), currentBooking.getId(), "A", licenseNumberA.getText(), ALicenseDateBooking.getValue());
            }
            if(BLicenseCheckBox.isSelected()){
                currentService.addLicenseInformationToPersistence(vehicle.getId(), currentBooking.getId(),"B", licenseNumberB.getText(), BLicenseDateBooking.getValue());
            }
            if(CLicenseCheckBox.isSelected()){
                currentService.addLicenseInformationToPersistence(vehicle.getId(), currentBooking.getId(), "C", licenseNumberC.getText(), CLicenseDateBooking.getValue());
            }
        }

    }

    private boolean checkAvailiabilityOfVehicle (Long id) {
        boolean isAvailable = true;
        List<Booking> allBookingsOfVehicle;
        allBookingsOfVehicle = currentService.getBookingsForVehicleFromPersistence(id);
        for (Booking booking: allBookingsOfVehicle) {
         if(isAvailable && !(booking.getStatus() == BookingStatus.CANCELED)) {
             if(booking.getPaidtime() == null) {
                 if (booking.getStartDate().isBefore(currentStartTime) && booking.getEndDate().isBefore(currentEndTime)) {
                     isAvailable = true;
                 } else if (booking.getStartDate().isAfter(currentStartTime) && booking.getEndDate().isAfter(currentEndTime)) {
                     isAvailable = true;
                 } else {
                     isAvailable = false;
                 }
             } else {
                 if (booking.getStartDate().isBefore(currentStartTime) && booking.getPaidtime().toLocalDateTime().isBefore(currentEndTime)) {
                     isAvailable = true;
                 } else if (booking.getStartDate().isAfter(currentStartTime) && booking.getPaidtime().toLocalDateTime().isAfter(currentEndTime)) {
                     isAvailable = true;
                 } else {
                     isAvailable = false;
                 }
             }
         }
        }
        return isAvailable;
    }

    @FXML
    private void openBookingTableView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking_tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingTableViewController) ? bookingTableViewController : null);

        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Bookings");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Tableview couldn't be changed");
        }
    }

    void fillBookingDetailView(Booking booking) {
        nameOfPersonBooking.setText(booking.getName());
        List<LicenseType> personLicenseList = booking.getPersonLicenseList();
        if(personLicenseList.contains(LicenseType.A)) {
            ALicenseCheckBox.setSelected(true);
            licenseNumberA.setText(booking.getLicensenumberA());
            ALicenseDateBooking.setValue(booking.getLicensedateA());
        }
        if(personLicenseList.contains(LicenseType.B)) {
            BLicenseCheckBox.setSelected(true);
            licenseNumberB.setText(booking.getLicensenumberB());
            BLicenseDateBooking.setValue(booking.getLicensedateB());
        }
        if(personLicenseList.contains(LicenseType.C)) {
            CLicenseCheckBox.setSelected(true);
            licenseNumberC.setText(booking.getLicensenumberC());
            CLicenseDateBooking.setValue(booking.getLicensedateC());
        }

        //TODO: PaymentType
        paymentNumberBooking.setText(booking.getPaymentNumber());
        LocalDate startDate = LocalDate.of(booking.getStartDate().getYear(),booking.getStartDate().getMonth(),booking.getStartDate().getDayOfMonth());
        int startHour = booking.getStartDate().getHour();
        int startMin = booking.getStartDate().getMinute();
        fromDatePickerBooking.setValue(startDate);
        fromHourPicker.getValueFactory().setValue(startHour);
        fromMinutePicker.getValueFactory().setValue(startMin);

        LocalDate endDate = LocalDate.of(booking.getEndDate().getYear(),booking.getEndDate().getMonth(),booking.getEndDate().getDayOfMonth());
        int endHour = booking.getEndDate().getHour();
        int endMin = booking.getEndDate().getMinute();
        toDatePickerBooking.setValue(endDate);
        toHourPicker.getValueFactory().setValue(endHour);
        toMinutePicker.getValueFactory().setValue(endMin);

        //TODO: createTimeLabelBooking.setText(booking.getCreatetime().toString());

        if(booking.getStartDate().isAfter(LocalDateTime.now())) {
            editButtonBookings.setVisible(true);
        }
        //TODO: tableview vehicles
        disableEverything();

    }

    public void disableEverything() {
        nameOfPersonBooking.setDisable(true);
        ALicenseCheckBox.setDisable(true);
        BLicenseCheckBox.setDisable(true);
        CLicenseCheckBox.setDisable(true);
        licenseNumberA.setDisable(true);
        licenseNumberB.setDisable(true);
        licenseNumberC.setDisable(true);
        ALicenseDateBooking.setDisable(true);
        BLicenseDateBooking.setDisable(true);
        CLicenseDateBooking.setDisable(true);
        paymentNumberBooking.setDisable(true);
        fromDatePickerBooking.setDisable(true);
        fromHourPicker.setDisable(true);
        fromMinutePicker.setDisable(true);
        toDatePickerBooking.setDisable(true);
        toHourPicker.setDisable(true);
        toMinutePicker.setDisable(true);
        bookedRadioButton.setSelected(true);

        vehiclesOfBooking.setVisible(false);
        vehiclesLabel.setVisible(false);
        hourlyPricesLabel.setVisible(false);
        pricesOfBooking.setVisible(false);
    }

    @FXML
    private void openVehicleTableView(ActionEvent event) {
        returnToVehicleTableView();
    }

    private void returnToVehicleTableView() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(tableViewController) ? tableViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Vehicles");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Tableview couldn't be changed");
        }
    }

    @FXML
    void editBooking(ActionEvent event) {

    }



}

