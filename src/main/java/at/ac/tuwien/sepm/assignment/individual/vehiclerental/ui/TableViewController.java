package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.entities.VehicleForFXML;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class TableViewController {

    private VehicleService currentService;
    private List<Vehicle> vehicleList;

    @FXML
    private TableView<Vehicle> tableViewVehicles;

    @FXML
    private TableColumn<Vehicle, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> buildyearColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> seatsColumn;

    @FXML
    private TableColumn<?, ?> licenseColumn;

    @FXML
    private TableColumn<?, ?> licenseplateColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> powerColumn;

    @FXML
    private TableColumn<?, ?> hourlyrateColumn;

    @FXML
    private TableColumn<?, ?> pictureColumn;

    @FXML
    private TableColumn<?, ?> createtimeColumn;

    @FXML
    private Button NewEntryTableView;

    @FXML
    private Button EditEntryTableView;

    @FXML
    private Button DeleteEntryTableView;

    @FXML
    private void initialize() {
        this.vehicleList = currentService.getListOfVehiclesFromPersistence();

    }


}
