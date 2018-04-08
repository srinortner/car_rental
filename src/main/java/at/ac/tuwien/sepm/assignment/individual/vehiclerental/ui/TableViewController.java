package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.OK;

public class TableViewController {

    private VehicleService currentService;
    private List<Vehicle> vehicleList;
    private DetailViewController detailViewController;
    private BookingController bookingController;
    private SearchController searchController;
    private BookingTableViewController bookingTableViewController;
    private VehicleController vehicleController;
    private IndexController indexController;
    private Stage primaryStage;

    public void setVehicleController(VehicleController vehicleController) {
        this.vehicleController = vehicleController;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    private boolean editing = false;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Scene returnToScene;
    private boolean editMode;

    public TableViewController(VehicleService currentService, DetailViewController detailViewController, BookingController bookingController, BookingTableViewController bookingTableViewController, SearchController searchController, Stage primaryStage) {
        this.currentService = currentService;
        this.detailViewController = detailViewController;
        detailViewController.setTableViewController(this);
        this.bookingController = bookingController;
        detailViewController.setTableViewController(this);
        bookingController.setTableViewController(this);
        this.searchController = searchController;
        this.bookingTableViewController = bookingTableViewController;
        bookingTableViewController.setTableViewController(this);
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

    @FXML
    private TableColumn<Vehicle, String> edittimeColumn;


    private ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();

    @FXML
    private Button NewEntryTableView;

    @FXML
    private Button DetailsTableView;

    @FXML
    private Button DeleteEntryTableView;

    @FXML
    private Button newBookingButtonTableView;

    @FXML
    private Button searchButtonTableview;

    @FXML
    private HBox hBoxEdit;

    @FXML
    private HBox hBoxOther;

    @FXML
    private HBox hBoxAllButtons;

    @FXML
    private void initialize() {
        setEditMode(false);
        initializeTableView(currentService.getListOfVehiclesFromPersistence());
    }

    private void initializeTableView(List<Vehicle> currentList) {
        this.vehicleList = currentList;

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        buildyearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBuildyear().toString()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        seatsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSeats().toString()));
        licenseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseType().toString()));
        licenseplateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicenseplate()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPowerSource().toString()));
        powerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPower().toString()));
        hourlyrateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHourlyRateCents().toString()));
        pictureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPicture()));
        createtimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatetime().toString()));
        edittimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdittime().toString()));


        List<Vehicle> temp = currentList;
        vehicleData = FXCollections.observableArrayList(temp);
        tableViewVehicles.setItems(vehicleData);
        tableViewVehicles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void openDetailView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/detailview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(detailViewController) ? detailViewController : null);

        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Details");
            int row = tableViewVehicles.getSelectionModel().getFocusedIndex();
            detailViewController.fill(vehicleList.get(row));
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed to DetailView");
        }

    }

    @FXML
    private void deleteEntry(ActionEvent event) {
        final ObservableList<Vehicle> selectedVehicles = tableViewVehicles.getSelectionModel().getSelectedItems();

        for (Vehicle vehicleToDelete : selectedVehicles) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("The vehicle will be deleted");
            alert.setContentText("Are you sure you want to delete " + vehicleToDelete.getName() + "?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                try {
                    currentService.deleteVehicleFromPersistence(vehicleToDelete);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            } else {
                alert.close();
            }

        }
        tableViewVehicles.getItems().removeAll(selectedVehicles);

    }


    @FXML
    private void changeToNewBookingView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingController) ? bookingController : null);
        final List<Vehicle> selectedVehicles = tableViewVehicles.getSelectionModel().getSelectedItems();

        if (selectedVehicles.isEmpty()) {
            new Alert(ERROR, "Please select the vehicles for your booking!", OK).showAndWait();
        } else {
            bookingController.setVehicleList(selectedVehicles);

            try {
                primaryStage.setScene(new Scene(fxmlLoader.load()));
                primaryStage.setTitle("New Booking");
                primaryStage.show();

            } catch (IOException e) {
                LOG.error("Stage couldn't be changed to Booking");
            }
        }
    }

    public void selectRowsForEditing(Booking booking) {
        List<Vehicle> toSelect = booking.getBookedVehicles();
        for (Vehicle vehicle : toSelect) {
            tableViewVehicles.getSelectionModel().select(vehicle);
        }

    }

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/search.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(searchController) ? searchController : null);
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Filter Dialog");

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(searchButtonTableview.getScene().getWindow());

            stage.showAndWait();
            if (searchController.isSearchButtonClicked()) {
                this.vehicleList = searchController.getFoundVehicles();
                initializeTableView(vehicleList);
                searchController.setSearchButtonClicked(false);
            }
        } catch (IOException e) {
            LOG.error("Search window couldn't be opened!");
        }

    }

    @FXML
    private void backButtonClicked() {
        final List<Vehicle> selectedVehicles = tableViewVehicles.getSelectionModel().getSelectedItems();
        if(selectedVehicles.isEmpty()){
            buildAlert(ERROR, "Please select at least one vehicle!").showAndWait();
        }
        else if (returnToScene != null) {
            primaryStage.setScene(returnToScene);

            bookingController.updateSelectedVehiclesOfBooking(selectedVehicles);
            primaryStage.show();
            this.setEditMode(false);
        } else {
            LOG.warn("returnToScene was not set");
        }
    }


    public void setReturnToScene(Scene returnToScene) {
        this.returnToScene = returnToScene;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        ObservableList<Node> hBoxAllButtonsChildren = hBoxAllButtons.getChildren();
        if (editMode) {
            if (!hBoxAllButtonsChildren.contains(hBoxEdit))
                hBoxAllButtonsChildren.add(hBoxEdit);
            if (hBoxAllButtonsChildren.contains(hBoxOther))
                hBoxAllButtonsChildren.remove(hBoxOther);
        } else {
            if (hBoxAllButtonsChildren.contains(hBoxEdit))
                hBoxAllButtonsChildren.remove(hBoxEdit);
            if (!hBoxAllButtonsChildren.contains(hBoxOther))
                hBoxAllButtonsChildren.add(hBoxOther);
        }
    }

    @FXML
    public void openNewVehicleView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicle.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(vehicleController) ? vehicleController : null);

        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("New Vehicle");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed to DetailView");
        }
    }

    @FXML
   private void backToIndexView(ActionEvent event) {
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
