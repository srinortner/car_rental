package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDateTime;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class BookingTableViewController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    @FXML
    private TableView<Booking> bookingTableView;

    @FXML
    private TableColumn<Booking, String> customerColumn;

    @FXML
    private TableColumn<Booking, String> fromColumn;

    @FXML
    private TableColumn<Booking, String> toColumn;

    @FXML
    private TableColumn<Booking, String> statusColumn;

    @FXML
    private TableColumn<Booking, String> totalPriceColumn;

    @FXML
    private Button detailViewButtonBookings;

    @FXML
    private Button finishButtonBooking;

    @FXML
    private Button cancelButtonBooking;

    @FXML
    private Button vehiclesButtonBooking;

    @FXML
    private Button addToBookingButton;

    @FXML
    private Button homeButtonBookingTableview;



    private List<Booking> bookingList = null;
    private BookingService currentService = null;
    private BookingController bookingController = null;
    private InvoiceController invoiceController = null;
    private TableViewController tableViewController = null;
    private IndexController indexController = null;
    private Stage primaryStage;
    private Vehicle vehicleForAddingToBooking = null;

    public BookingController getBookingController() {
        return bookingController;
    }

    public void setBookingController(BookingController bookingController) {
        this.bookingController = bookingController;

    }

    public void setTableViewController(TableViewController tableViewController) {
        this.tableViewController = tableViewController;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    private ObservableList<Booking> bookingData = FXCollections.observableArrayList();

    public BookingTableViewController(BookingService currentService, InvoiceController invoiceController, Stage primaryStage) {
        this.currentService = currentService;
        this.primaryStage = primaryStage;
        this.invoiceController = invoiceController;
        invoiceController.setBookingTableViewController(this);
    }

    @FXML
    private void initialize() {
        try {
            bookingList = currentService.getAllBookingsFromPersistence();
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalPrice().toString()));
        fromColumn.setSortType(TableColumn.SortType.DESCENDING);
        List<Booking> temp = null;
        try {
            temp = currentService.getAllBookingsFromPersistence();
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        bookingData = FXCollections.observableArrayList(temp);
        bookingTableView.setItems(bookingData);


    }

    @FXML
    void cancelBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();

        if (!(selectedBooking.getStatus().equals(BookingStatus.BOOKED)) || selectedBooking.getStartDate().isBefore(LocalDateTime.now())) {
            buildAlert(ERROR,"This booking can't be canceled!").showAndWait();
        //    new Alert(ERROR, "This booking can't be canceled!", OK).showAndWait();
        } else {
            try {
                currentService.cancelBookingInPersistence(selectedBooking);
            } catch (ServiceException | InvalidBookingException e) {
                LOG.error("Booking couldn't be canceled in Service!", e);
            }
            selectedBooking.setStatus(BookingStatus.CANCELED);
            bookingTableView.refresh();
        }

    }

    @FXML
    void finishBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
        if(!selectedBooking.getStatus().equals(BookingStatus.BOOKED) || selectedBooking.getStartDate().isBefore(LocalDateTime.now())){
            buildAlert(ERROR,"This booking is already finished!").showAndWait();
           // new Alert(ERROR,"This booking is already finished!", OK).showAndWait();
        } else {
            try {
                currentService.finishBookingInPersistence(selectedBooking);
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
            selectedBooking.setStatus(BookingStatus.PAID);
            bookingTableView.refresh();
        }
    }



    @FXML
    private void showDetailViewOfBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
        if(selectedBooking.getStatus().equals(BookingStatus.BOOKED)) {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking.fxml"));
            fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingController) ? bookingController : null);
            try {
                primaryStage.setScene(new Scene(fxmlLoader.load()));
                primaryStage.setTitle("Booking Details");
                bookingController.fillBookingDetailView(selectedBooking);
                primaryStage.show();

            } catch (IOException e) {
                LOG.error("Stage for Tableview couldn't be changed", e);
            }
        } else {
            final FXMLLoader fxmlLoaderInvoice = new FXMLLoader(getClass().getResource("/fxml/invoice.fxml"));
            fxmlLoaderInvoice.setControllerFactory(classToLoad -> classToLoad.isInstance(invoiceController) ? invoiceController : null);
            try {
                primaryStage.setScene(new Scene(fxmlLoaderInvoice.load()));
                primaryStage.setTitle("Invoice Details");
                invoiceController.fillInvoiceView(selectedBooking);
                primaryStage.show();

            } catch (IOException e) {
                LOG.error("Stage for Tableview couldn't be changed", e);
            }
        }
    }


    @FXML
   private void showVehicleTableView(ActionEvent event) {
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
    private void addToBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
        try {
            currentService.validateAddingVehicleToExistingBooking(vehicleForAddingToBooking,selectedBooking);
            if(selectedBooking.getLicensedateA() != null){
                License licenseA = new License(LicenseType.A,selectedBooking.getLicensedateA(),selectedBooking.getLicensenumberA());
                try {
                    currentService.addLicenseInformationToPersistence(vehicleForAddingToBooking,selectedBooking,licenseA);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if(selectedBooking.getLicensedateB() != null){
                License licenseB = new License(LicenseType.B,selectedBooking.getLicensedateB(),selectedBooking.getLicensenumberB());
                try {
                    currentService.addLicenseInformationToPersistence(vehicleForAddingToBooking,selectedBooking,licenseB);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if(selectedBooking.getLicensedateC() != null){
                License licenseC = new License(LicenseType.C,selectedBooking.getLicensedateC(),selectedBooking.getLicensenumberC());
                try {
                    currentService.addLicenseInformationToPersistence(vehicleForAddingToBooking,selectedBooking,licenseC);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            if(selectedBooking.getPersonLicenseList().isEmpty()){
                try {
                    currentService.addLicenseInformationToPersistence(vehicleForAddingToBooking,selectedBooking,null);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
            try {
                currentService.updateTotalPrice(selectedBooking);
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
            buildAlert(INFORMATION, "Vehicle was added to Booking!").showAndWait();
            changeToNormalMode();
        } catch (InvalidBookingException e) {
            buildAlert(ERROR,e.getMessage()).showAndWait();
            LOG.error("Vehicle couldn't be added to booking!");
        }



    }

    public void changeToAddToBookingMode(Vehicle vehicle){
        vehicleForAddingToBooking = vehicle;
        addToBookingButton.setVisible(true);
        addToBookingButton.setDisable(false);

        cancelButtonBooking.setVisible(false);
        cancelButtonBooking.setDisable(true);
        detailViewButtonBookings.setVisible(false);
        detailViewButtonBookings.setDisable(true);
        vehiclesButtonBooking.setVisible(false);
        vehiclesButtonBooking.setDisable(true);
        finishButtonBooking.setVisible(false);
        finishButtonBooking.setDisable(true);


    }

    private void changeToNormalMode(){
        vehicleForAddingToBooking = null;
        addToBookingButton.setVisible(false);
        addToBookingButton.setDisable(true);

        cancelButtonBooking.setVisible(true);
        cancelButtonBooking.setDisable(false);
        detailViewButtonBookings.setVisible(true);
        detailViewButtonBookings.setDisable(false);
        vehiclesButtonBooking.setVisible(true);
        vehiclesButtonBooking.setDisable(false);
        finishButtonBooking.setVisible(true);
        finishButtonBooking.setDisable(false);

        bookingTableView.refresh();
    }

    @FXML
    void backToIndexView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/index.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(indexController) ? indexController : null);

        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Home");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed to DetailView");
        }
    }

}

