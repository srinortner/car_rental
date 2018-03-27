package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InvoiceController {

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
    private TableColumn<Vehicle, String> VehicleLicenseplateColumn;

    @FXML
    private TableColumn<Vehicle, String> VehiclePriceColumn;

    public void fillInvoiceView(Booking booking) {
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
        //TODO: Tableview vehicles

    }

}
