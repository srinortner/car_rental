package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class BookingTableViewController {

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

    private ObservableList<Booking> bookingData = FXCollections.observableArrayList();

    public BookingTableViewController(BookingService currentService) {
        this.currentService = currentService;
    }

    @FXML
    private void initialize() {
        bookingList = currentService.getAllBookingsFromPersistence();

        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalPrice().toString()));

        List<Booking> temp = currentService.getAllBookingsFromPersistence();
        bookingData = FXCollections.observableArrayList(temp);
        bookingTableView.setItems(bookingData);
        bookingTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    void cancelBooking(ActionEvent event) {

    }

    @FXML
    void finishBooking(ActionEvent event) {

    }

    @FXML
    void showDetailViewOfBooking(ActionEvent event) {

    }

    @FXML
    void showVehicleTableView(ActionEvent event) {

    }

}

