package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

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
    private TableView<?> tableViewBookedVehiclesInvoice;

    @FXML
    private TableColumn<?, ?> vehicleNameColumn;

    @FXML
    private TableColumn<?, ?> vehicleBuildyearColumn;

    @FXML
    private TableColumn<?, ?> VehicleLicenseplateColumn;

    @FXML
    private TableColumn<?, ?> VehiclePriceColumn;

}
