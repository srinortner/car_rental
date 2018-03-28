package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.OK;

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

    private List<Booking> bookingList = null;
    private BookingService currentService = null;
    private BookingController bookingController = null;
    private InvoiceController invoiceController = null;
    private Stage primaryStage;

    public BookingController getBookingController() {
        return bookingController;
    }

    public void setBookingController(BookingController bookingController) {
        this.bookingController = bookingController;

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
        bookingList = currentService.getAllBookingsFromPersistence();

        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalPrice().toString()));
        fromColumn.setSortType(TableColumn.SortType.DESCENDING);
        List<Booking> temp = currentService.getAllBookingsFromPersistence();
        bookingData = FXCollections.observableArrayList(temp);
        bookingTableView.setItems(bookingData);


    }

    @FXML
    void cancelBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
        if (!selectedBooking.getStatus().equals(BookingStatus.BOOKED) && selectedBooking.getStartDate().isAfter(LocalDateTime.now())) {
            new Alert(ERROR, "This booking can't be canceled!", OK).showAndWait();
        } else {
            currentService.cancelBookingInPersistence(selectedBooking);
            selectedBooking.setStatus(BookingStatus.CANCELED);
            bookingTableView.refresh();
        }

    }

    @FXML
    void finishBooking(ActionEvent event) {
        Booking selectedBooking = bookingTableView.getSelectionModel().getSelectedItem();
        if(!selectedBooking.getStatus().equals(BookingStatus.BOOKED) && selectedBooking.getStartDate().isAfter(LocalDateTime.now())){
            new Alert(ERROR,"This booking is already finished!", OK).showAndWait();
        } else {
            currentService.finishBookingInPersistence(selectedBooking);
            selectedBooking.setStatus(BookingStatus.PAID);
            bookingTableView.refresh();
        }
    }



    @FXML
    void showDetailViewOfBooking(ActionEvent event) {
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
                LOG.error("Stage for Tableview couldn't be changed");
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
                LOG.error("Stage for Tableview couldn't be changed");
            }
        }
    }

    @FXML
    void showVehicleTableView(ActionEvent event) {
        //TODO: Change to VehicleTableView

    }

}

