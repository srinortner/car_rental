package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VehicleController vehicleController = null;
    private TableViewController tableViewController = null;
    private BookingTableViewController bookingTableViewController = null;
    private StatisticsController statisticsController = null;
    private Stage primaryStage = null;

    public IndexController(VehicleController vehicleController, TableViewController tableViewController,  BookingTableViewController bookingTableViewController, StatisticsController statisticsController, Stage primaryStage) {
        this.vehicleController = vehicleController;
        this.tableViewController = tableViewController;
        this.bookingTableViewController = bookingTableViewController;
        this.statisticsController = statisticsController;
        this.primaryStage = primaryStage;
        statisticsController.setIndexController(this);
        tableViewController.setIndexController(this);
        vehicleController.setIndexController(this);
        bookingTableViewController.setIndexController(this);
    }

    @FXML
    void showBookingTableViewController(ActionEvent event) {

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

    @FXML
    void showStatisticsController(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/statistics.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(statisticsController) ? statisticsController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Statistics");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed", e);
        }
    }

    @FXML
    void showTableViewController(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(tableViewController) ? tableViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Vehicles");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed", e);
        }
    }

    @FXML
    void showVehicleController(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicle.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(vehicleController) ? vehicleController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("New Vehicle");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed", e);
        }
    }

}
