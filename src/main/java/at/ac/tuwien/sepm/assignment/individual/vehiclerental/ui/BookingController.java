package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.SpinnerFactory.buildSpinner;
import static java.util.Collections.*;
import static java.util.stream.Collectors.joining;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.ButtonType.OK;

public class BookingController {

    private List<Vehicle> vehicleList = emptyList();
    private BookingService currentService;
    private VehicleService currentVehicleService;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private TableViewController tableViewController;
    private BookingTableViewController bookingTableViewController;
    private Stage primaryStage;

    public TableViewController getTableViewController() {
        return tableViewController;
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
    private Button editVehicleListButton;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TableView<Vehicle> tableViewBookedVehicles;

    @FXML
    private TableColumn<Vehicle, String> vehicleNameColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleBuildyearColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleLicenseplateColumn;

    @FXML
    private TableColumn<Vehicle, String> vehiclePriceColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label showTotalPriceLabel;


    @FXML
    private Button editButtonBookings;
    private Booking currentBooking = null;
    private LocalDateTime currentStartTime = null;
    private LocalDateTime currentEndTime = null;
    private boolean vehiclesAvailable = true;

    @FXML
    private Label hourlyPricesLabel;

    @FXML
    private Label vehiclesLabel;

    private List<Vehicle> vehiclesOfBookingList = new ArrayList<>();
    private ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();

    public void setTableViewController(TableViewController tableViewController) {
        this.tableViewController = tableViewController;

    }

    public BookingController(BookingService currentService, VehicleService currentVehicleService, BookingTableViewController bookingTableViewController, Stage primaryStage) {
        this.currentService = currentService;
        this.currentVehicleService = currentVehicleService;
        this.bookingTableViewController = bookingTableViewController;
        this.primaryStage = primaryStage;
        bookingTableViewController.setBookingController(this);
    }

    public void updateSelectedVehiclesOfBooking(List<Vehicle> vehiclesOfBookingList) {
        this.vehiclesOfBookingList = vehiclesOfBookingList;
        initializeTableView();
    }

    @FXML
    private void initialize() {
        vehiclesOfBooking.setText(vehicleList.toString());
        List<Integer> pricesPerVehicle = new LinkedList<>();
        for (Vehicle vehicle : vehicleList) {
            pricesPerVehicle.add(vehicle.getHourlyRateCents());
        }
        pricesOfBooking.setText(pricesPerVehicle.toString());
        toDatePickerBooking.setValue(LocalDate.now());
        fromDatePickerBooking.setValue(LocalDate.now());


        SpinnerValueFactory<Integer> valueFactoryHour = buildSpinner(23);

        SpinnerValueFactory<Integer> valueFactoryHourTo = buildSpinner(23);

        valueFactoryHour.setValue(0);
        valueFactoryHourTo.setValue(0);
        fromHourPicker.setValueFactory(valueFactoryHour);
        toHourPicker.setValueFactory(valueFactoryHourTo);

        SpinnerValueFactory<Integer> valueFactoryMin = buildSpinner(59);

        SpinnerValueFactory<Integer> valueFactoryMinTo = buildSpinner(59);

        valueFactoryMin.setValue(0);
        valueFactoryMinTo.setValue(0);
        fromMinutePicker.setValueFactory(valueFactoryMin);
        toMinutePicker.setValueFactory(valueFactoryMinTo);

        ChangeListener<Integer> updateTableViewOnSpinnerChange = (o,ov,nv) -> { this.updateCurrentDateTime(); this.tableViewBookedVehicles.refresh();};
        ChangeListener<LocalDate> updateTableViewOnDateChange = (o,ov,nv) -> { this.updateCurrentDateTime(); this.tableViewBookedVehicles.refresh();};
        fromHourPicker.valueProperty().addListener(updateTableViewOnSpinnerChange);
        toHourPicker.valueProperty().addListener(updateTableViewOnSpinnerChange);
        fromMinutePicker.valueProperty().addListener(updateTableViewOnSpinnerChange);
        toMinutePicker.valueProperty().addListener(updateTableViewOnSpinnerChange);
        fromDatePickerBooking.valueProperty().addListener(updateTableViewOnDateChange);
        toDatePickerBooking.valueProperty().addListener(updateTableViewOnDateChange);
    }

    private void updateCurrentDateTime() {
        currentStartTime = LocalDateTime.of(fromDatePickerBooking.getValue(), LocalTime.of(fromHourPicker.getValue(), fromMinutePicker.getValue()));
        currentEndTime = LocalDateTime.of(toDatePickerBooking.getValue(), LocalTime.of(toHourPicker.getValue(), toMinutePicker.getValue()));
    }

    @FXML
    private void saveBooking(ActionEvent event) {
        createNewBooking();
        saveCurrentBooking();
    }

    private void saveCurrentBooking() {
        if (vehiclesAvailable) {
            if (!cancelingPossible()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Free canceling will not be possible for this booking!");
                alert.setContentText("Do you want to continue the booking anyways?");

                if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    //String name, PaymentType paymentType, String paymentNumber, LocalDate startDate, LocalDate endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status

                    try {
                        currentService.addBookingToPersistence(currentBooking);
                        saveLicenseInformation();
                        buildAlert(INFORMATION, "Your booking was added!").showAndWait();
                    } catch (InvalidBookingException e) {
                        LOG.error("Booking couldn't be added to Persistence! {}", e.getMessage());
                        buildAlert(ERROR,e.getConstraintViolations().stream().collect(joining("\n"))).showAndWait();
                     //   new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
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
                    buildAlert(ERROR,e.getConstraintViolations().stream().collect(joining("\n"))).showAndWait();
                   // new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
                }
                returnToVehicleTableView();
            }
        } else {
            new Alert(ERROR, "One of the vehicles is already booked during that time, please select another time!", OK).showAndWait();
        }
    }


    private void createNewBooking() {
        Long currentID = null;
        if(currentBooking != null){
            currentID = currentBooking.getId();
        }
        String currentName = nameOfPersonBooking.getText();
        PaymentType currentPaymentType = null;
        if (creditCardButtonBooking.isSelected()) {
            currentPaymentType = PaymentType.CREDITCARD;
        } else {
            currentPaymentType = PaymentType.IBAN;
        }
        String currentPaymentNumber = paymentNumberBooking.getText();

        this.updateCurrentDateTime();

        int dailyPrice = 0;
        vehiclesAvailable = true;
        for (Vehicle vehicle : vehicleList) {
            dailyPrice += vehicle.getHourlyRateCents() * 24;
            if (!currentService.checkAvailiabilityOfVehicle(vehicle, currentStartTime, currentEndTime)) {
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
        currentBooking.setId(currentID);
        if (ALicenseCheckBox.isSelected()) {
            currentPersonLicenseList.add(LicenseType.A);
            currentBooking.setLicensedateA(ALicenseDateBooking.getValue());
            currentBooking.setLicensenumberA(licenseNumberA.getText());
        }
        if (BLicenseCheckBox.isSelected()) {
            currentPersonLicenseList.add(LicenseType.B);
            currentBooking.setLicensedateB(BLicenseDateBooking.getValue());
            currentBooking.setLicensenumberB(licenseNumberB.getText());
        }
        if (CLicenseCheckBox.isSelected()) {
            currentPersonLicenseList.add(LicenseType.C);
            currentBooking.setLicensedateC(CLicenseDateBooking.getValue());
            currentBooking.setLicensenumberC(licenseNumberC.getText());
        }
        currentBooking.setPersonLicenseList(currentPersonLicenseList);


    }

    private boolean cancelingPossible() {
        if (paidRadioButton.isSelected()) {
            return false;
        }
        if (LocalDateTime.now().plusWeeks(1).isAfter(currentStartTime)) {
            return false;
        }
        return true;
    }

    private void saveLicenseInformation() {
        for (Vehicle vehicle : vehicleList) {
            if (ALicenseCheckBox.isSelected()) {
                License licenseA = new License(LicenseType.A,ALicenseDateBooking.getValue(), licenseNumberA.getText());
                try {
                    currentService.addLicenseInformationToPersistence(vehicle, currentBooking, licenseA);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if (BLicenseCheckBox.isSelected()) {
                License licenseB = new License(LicenseType.B,BLicenseDateBooking.getValue(), licenseNumberB.getText());
                try {
                    currentService.addLicenseInformationToPersistence(vehicle, currentBooking, licenseB);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if (CLicenseCheckBox.isSelected()) {
                License licenseC = new License(LicenseType.C,CLicenseDateBooking.getValue(), licenseNumberC.getText());
                try {
                    currentService.addLicenseInformationToPersistence(vehicle, currentBooking, licenseC);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if(!ALicenseCheckBox.isSelected() && !BLicenseCheckBox.isSelected() && !CLicenseCheckBox.isSelected()){
                try {
                    currentService.addLicenseInformationToPersistence(vehicle,currentBooking,null);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }

        }

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
        disableEverything();
        nameOfPersonBooking.setText(booking.getName());
        List<LicenseType> personLicenseList = booking.getPersonLicenseList();
        if (personLicenseList.contains(LicenseType.A)) {
            ALicenseCheckBox.setSelected(true);
            licenseNumberA.setText(booking.getLicensenumberA());
            ALicenseDateBooking.setValue(booking.getLicensedateA());
        }
        if (personLicenseList.contains(LicenseType.B)) {
            BLicenseCheckBox.setSelected(true);
            licenseNumberB.setText(booking.getLicensenumberB());
            BLicenseDateBooking.setValue(booking.getLicensedateB());
        }
        if (personLicenseList.contains(LicenseType.C)) {
            CLicenseCheckBox.setSelected(true);
            licenseNumberC.setText(booking.getLicensenumberC());
            CLicenseDateBooking.setValue(booking.getLicensedateC());
        }

        if (booking.getPaymentType().equals(PaymentType.CREDITCARD)) {
            creditCardButtonBooking.setSelected(true);
        } else {
            IBANButtonBooking.setSelected(true);
        }
        paymentNumberBooking.setText(booking.getPaymentNumber());
        LocalDate startDate = LocalDate.of(booking.getStartDate().getYear(), booking.getStartDate().getMonth(), booking.getStartDate().getDayOfMonth());
        int startHour = booking.getStartDate().getHour();
        int startMin = booking.getStartDate().getMinute();
        fromDatePickerBooking.setValue(startDate);
        fromHourPicker.getValueFactory().setValue(startHour);
        fromMinutePicker.getValueFactory().setValue(startMin);

        LocalDate endDate = LocalDate.of(booking.getEndDate().getYear(), booking.getEndDate().getMonth(), booking.getEndDate().getDayOfMonth());
        int endHour = booking.getEndDate().getHour();
        int endMin = booking.getEndDate().getMinute();
        toDatePickerBooking.setValue(endDate);
        toHourPicker.getValueFactory().setValue(endHour);
        toMinutePicker.getValueFactory().setValue(endMin);

        createTimeLabelBooking.setText(booking.getCreatetime().toString());

        if (booking.getStartDate().isAfter(LocalDateTime.now())) {
            editButtonBookings.setVisible(true);
        }


        currentStartTime = booking.getStartDate();
        currentEndTime = booking.getEndDate();
        double totalpriceInEuro = booking.getTotalPrice()/100;
        showTotalPriceLabel.setText(String.valueOf(totalpriceInEuro) + "€");

        List<Long> vehicleIDsOfBooking = null;
        try {
            vehicleIDsOfBooking = currentService.getVehicleIDsFromPersistence(booking);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

        vehiclesOfBookingList = new ArrayList<>();
        for (Long id : vehicleIDsOfBooking) {
            Vehicle vehicle = null;
            try {
                vehicle = currentVehicleService.getVehiclesByIDFromPersistence(id);
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
            vehiclesOfBookingList.add(vehicle);
        }
        initializeTableView();
        currentBooking = booking;
        currentBooking.setBookedVehicles(vehiclesOfBookingList);

    }

    private void initializeTableView() {
        tableViewBookedVehicles.setVisible(true);

        vehicleNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        vehicleBuildyearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuildyear().toString()));
        vehicleLicenseplateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseplate()));
        vehiclePriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(renderPriceForVehicleInCurrentBookingInEuro(cellData.getValue())));
        List<Vehicle> temp = vehiclesOfBookingList;
        vehicleData = FXCollections.observableArrayList(temp);
        tableViewBookedVehicles.setItems(vehicleData);
        showTotalPriceLabel.setText(calculateCurrentTotalprice());

    }

    private String renderPriceForVehicleInCurrentBookingInEuro(Vehicle vehicle) {
        int pricePerMinute = vehicle.getHourlyRateCents() / 60;
        float priceForVehicle = (float) (Duration.between(this.currentStartTime, this.currentEndTime).toMinutes() * pricePerMinute) / 100;
        return String.format("%.2f €", priceForVehicle);
    }

    private Double caluclatePriceForVehicleInCurrentBookingCent(Vehicle vehicle){
        double pricePerMinute = vehicle.getHourlyRateCents() / 60;
        double priceForVehicle = (double) (Duration.between(this.currentStartTime, this.currentEndTime).toMinutes() * pricePerMinute) / 100;
        return priceForVehicle;
    }

    private String calculateCurrentTotalprice(){
        Double priceInEu = 0.0;
        for (Vehicle vehicle:vehiclesOfBookingList) {
           double vehiclePrice = caluclatePriceForVehicleInCurrentBookingCent(vehicle);
            priceInEu += vehiclePrice ;
        }
        return String.format("%.2f €", priceInEu);
    }

    private void disableEverything() {
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
        creditCardButtonBooking.setDisable(true);
        IBANButtonBooking.setDisable(true);
        paymentNumberBooking.setDisable(true);
        fromDatePickerBooking.setDisable(true);
        fromHourPicker.setDisable(true);
        fromMinutePicker.setDisable(true);
        toDatePickerBooking.setDisable(true);
        toHourPicker.setDisable(true);
        toMinutePicker.setDisable(true);
        bookedRadioButton.setSelected(true);
        showTotalPriceLabel.setVisible(true);
        totalPriceLabel.setVisible(true);
        saveButtonBooking.setDisable(true);
        editButtonBookings.setVisible(true);
        editButtonBookings.setDisable(false);

        vehiclesOfBooking.setVisible(false);
        vehiclesLabel.setVisible(false);
        hourlyPricesLabel.setVisible(false);
        pricesOfBooking.setVisible(false);
        saveChangesButton.setDisable(true);
        saveChangesButton.setVisible(false);
        editVehicleListButton.setDisable(true);
        editVehicleListButton.setVisible(false);

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
            LOG.error("Stage for Tableview couldn't be changed", e);
        }
    }


    @FXML
    private void editBooking(ActionEvent event) {
        enableEverything();

    }

    private void enableEverything() {
        bookedRadioButton.setSelected(true);
        nameOfPersonBooking.setDisable(false);
        ALicenseCheckBox.setDisable(false);
        BLicenseCheckBox.setDisable(false);
        CLicenseCheckBox.setDisable(false);
        licenseNumberA.setDisable(false);
        licenseNumberB.setDisable(false);
        licenseNumberC.setDisable(false);
        ALicenseDateBooking.setDisable(false);
        BLicenseDateBooking.setDisable(false);
        CLicenseDateBooking.setDisable(false);
        creditCardButtonBooking.setDisable(false);
        IBANButtonBooking.setDisable(false);
        paymentNumberBooking.setDisable(false);
        fromDatePickerBooking.setDisable(false);
        fromHourPicker.setDisable(false);
        fromMinutePicker.setDisable(false);
        toDatePickerBooking.setDisable(false);
        toHourPicker.setDisable(false);
        toMinutePicker.setDisable(false);
        bookedRadioButton.setSelected(false);

        vehiclesOfBooking.setVisible(false);
        vehiclesLabel.setVisible(false);
        hourlyPricesLabel.setVisible(false);
        pricesOfBooking.setVisible(false);
        editButtonBookings.setVisible(false);
        saveButtonBooking.setDisable(true);
        saveChangesButton.setVisible(true);
        editVehicleListButton.setVisible(true);
        saveChangesButton.setDisable(false);
        editVehicleListButton.setDisable(false);

    }

    @FXML
    void changeToBookingTableView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(tableViewController) ? tableViewController : null);

        try {
            Scene returnToScene = primaryStage.getScene();
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Vehicles");
            tableViewController.setEditMode(true);
            tableViewController.setReturnToScene(returnToScene);
            tableViewController.selectRowsForEditing(currentBooking);
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Tableview couldn't be changed");
        }
    }

    @FXML
    void updateBooking(ActionEvent event) {
        //damit createNewBooking wiederverwendet werden kann
        vehicleList = vehiclesOfBookingList;
        createNewBooking();
        try {
            currentService.updateBookingInPersistence(currentBooking);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        saveLicenseInformation();
        buildAlert(INFORMATION, "Your booking was updated!").showAndWait();
        disableEverything();


    }



}

