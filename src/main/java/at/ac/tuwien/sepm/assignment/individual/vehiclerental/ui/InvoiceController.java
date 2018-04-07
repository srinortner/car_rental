package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvoiceController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BookingTableViewController bookingTableViewController;
    private BookingService bookingService;
    private VehicleService vehicleService;
    private List<Vehicle> vehiclesOfInvoiceList = new ArrayList<>();
    private ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();
    private HashMap<Long,Integer> currentPricesOfVehicles = new HashMap<>();

    public InvoiceController(BookingService bookingService, VehicleService vehicleService, Stage primaryStage) {
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
        this.primaryStage = primaryStage;
    }

    public BookingTableViewController getBookingTableViewController() {
        return bookingTableViewController;
    }
    private Stage primaryStage = null;

    public void setBookingTableViewController(BookingTableViewController bookingTableViewController) {
        this.bookingTableViewController = bookingTableViewController;

    }

    @FXML
    private Label numberLabelInvoice;

    @FXML
    private Label fromLabelInvoice;

    @FXML
    private Label statusLabelInvoice;

    @FXML
    private Label toLabelInvoice;

    @FXML
    private Label closingTimeLabelInvoice;

    @FXML
    private Label totalPriceLabelInvoice;

    @FXML
    private TableView<Vehicle> tableViewBookedVehiclesInvoice;

    @FXML
    private TableColumn<Vehicle, String> vehicleNameColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleBuildyearColumn;

    @FXML
    private TableColumn<Vehicle, String> vehicleLicenseplateColumn;

    @FXML
    private TableColumn<Vehicle, String> vehiclePriceColumn;


    public void fillInvoiceView(Booking booking) {
        numberLabelInvoice.setText(booking.getInvoiceNumber().toString());
        fromLabelInvoice.setText(booking.getStartDate().toString());
        toLabelInvoice.setText(booking.getEndDate().toString());
        statusLabelInvoice.setText(booking.getStatus().toString());
        closingTimeLabelInvoice.setText(booking.getPaidtime().toLocalDateTime().toString());
        double priceInEUR = booking.getTotalPrice()/100;
        if(booking.getStatus().equals(BookingStatus.CANCELED)) {
            totalPriceLabelInvoice.setText("€" + priceInEUR + " (" + booking.getCancelingFeeInPercent() + "% cancellation fee)");
        } else {
            totalPriceLabelInvoice.setText("€" + priceInEUR);
        }

        List<Long> vehicleIDsOfBooking = bookingService.getVehicleIDsFromPersistence(booking);
        for (Long id: vehicleIDsOfBooking) {
            Vehicle vehicle = vehicleService.getVehiclesByIDFromPersistence(id);
            vehiclesOfInvoiceList.add(vehicle);
            Integer priceOfVehicle = 0;
            LocalDateTime i = booking.getStartDate();
            Integer pricePerMinute = vehicle.getHourlyRateCents()/60;
            while (!i.equals(booking.getEndDate())) {
                priceOfVehicle += pricePerMinute;
                i = i.plusMinutes(1);
            }
            currentPricesOfVehicles.put(vehicle.getId(),priceOfVehicle);
        }
        initializeTableView();

    }

    public void initializeTableView() {

        vehicleNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        vehicleBuildyearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuildyear().toString()));
        vehicleLicenseplateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseplate()));
        vehiclePriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(currentPricesOfVehicles.get(cellData.getValue().getId()).toString()));
        List<Vehicle> temp = vehiclesOfInvoiceList;
        vehicleData = FXCollections.observableArrayList(temp);
        tableViewBookedVehiclesInvoice.setItems(vehicleData);
    }

    @FXML
    void changeToBookingTableView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking_tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingTableViewController) ? bookingTableViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Bookings");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed", e);
        }
    }




}
