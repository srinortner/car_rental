package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class TableViewController {

    private VehicleService currentService;
    private List<Vehicle> vehicleList;
    private DetailViewController detailViewController;
    private Stage primaryStage;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TableViewController(VehicleService currentService, DetailViewController detailViewController, Stage primaryStage) {
        this.currentService = currentService;
        this.detailViewController = detailViewController;
        this.primaryStage = primaryStage;
    }

    @FXML
    private TableView<Vehicle> tableViewVehicles;

    @FXML
    private TableColumn<Vehicle, String> nameColumn;

    @FXML
    private TableColumn<Vehicle, String> buildyearColumn;

    @FXML
    private TableColumn<Vehicle, String> descriptionColumn;

    @FXML
    private TableColumn<Vehicle, String> seatsColumn;

    @FXML
    private TableColumn<Vehicle, String> licenseColumn;

    @FXML
    private TableColumn<Vehicle, String> licenseplateColumn;

    //TODO: Enum?
    @FXML
    private TableColumn<Vehicle, String> typeColumn;

    @FXML
    private TableColumn<Vehicle, String> powerColumn;

    @FXML
    private TableColumn<Vehicle, String> hourlyrateColumn;

    @FXML
    private TableColumn<Vehicle, String> pictureColumn;

    @FXML
    private TableColumn<Vehicle, String> createtimeColumn;

    private ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();

    @FXML
    private Button NewEntryTableView;

    @FXML
    private Button DetailsTableView;

    @FXML
    private Button DeleteEntryTableView;

    @FXML
    private void initialize() {
        this.vehicleList = currentService.getListOfVehiclesFromPersistence();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        buildyearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuildyear().toString()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        seatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSeats().toString()));
       // licenseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseType().toString()));
        licenseplateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseplate()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPowerSource().toString()));
        powerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPower().toString()));
        hourlyrateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHourlyRateCents().toString()));
        pictureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPicture()));
        createtimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatetime().toString()));

        List<Vehicle> temp = currentService.getListOfVehiclesFromPersistence();
        vehicleData = FXCollections.observableArrayList(temp);
        tableViewVehicles.setItems(vehicleData);

    }

    @FXML
    void openDetailView(ActionEvent event) {
        detailViewController.fill(vehicleList.get(0));
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/detailview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(detailViewController) ? detailViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Details");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed to DetailView");
        }

    }


}
