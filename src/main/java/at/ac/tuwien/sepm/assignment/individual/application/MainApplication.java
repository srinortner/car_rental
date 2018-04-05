package at.ac.tuwien.sepm.assignment.individual.application;

import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleBookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleVehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui.*;
import fxml.StatisticsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public final class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void stop() throws Exception {
        super.stop();
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setup application
        primaryStage.setTitle("Universe Calculator");
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        // initiate service and controller
        // UniverseService universeService = new SimpleUniverseService();
        //  UniverseController universeController = new UniverseController(universeService);

        VehicleDAO vehicleDAO = new SimpleVehicleDAO();
        BookingDAO bookingDAO = new SimpleBookingDAO();
        VehicleService vehicleService = new SimpleVehicleService(vehicleDAO);
        BookingService bookingService = new SimpleBookingService(bookingDAO, vehicleService);
        InvoiceController invoiceController = new InvoiceController(bookingService, vehicleService);
        BookingTableViewController bookingTableViewController = new BookingTableViewController(bookingService, invoiceController,primaryStage);
        BookingController bookingController = new BookingController(bookingService, vehicleService, bookingTableViewController, primaryStage);
        SearchController searchController = new SearchController(vehicleService, bookingService);
        DetailViewController detailViewController = new DetailViewController(vehicleService,bookingController, bookingTableViewController,primaryStage);
        TableViewController tableViewController = new TableViewController(vehicleService, detailViewController, bookingController, bookingTableViewController,searchController, primaryStage);
        VehicleController vehicleController = new VehicleController(vehicleService, tableViewController, primaryStage);
        StatisticsController statisticsController = new StatisticsController();


        // prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicle.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(vehicleController) ? vehicleController : null);
        primaryStage.setScene(new Scene(fxmlLoader.load()));
    //    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxml/statistics.fxml"));
      //  fxmlLoader2.setControllerFactory(param -> param.isInstance(statisticsController) ? statisticsController : null);
        //primaryStage.setScene(new Scene(fxmlLoader2.load()));

        // show application
        primaryStage.show();
        primaryStage.toFront();
        LOG.debug("Application startup complete");
    }

    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        Application.launch(MainApplication.class, args);
    }

}
