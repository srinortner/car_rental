package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseDouble;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;
import static java.util.stream.Collectors.joining;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class DetailViewController {


    private VehicleService vehicleService;

    private File picture = null;
    private Vehicle vehicle = null;
    private Stage primaryStage = null;
    private  TableViewController tableViewController = null;
    private BookingController bookingController = null;
    private BookingTableViewController bookingTableViewController = null;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public DetailViewController(VehicleService vehicleService, BookingController bookingController, BookingTableViewController bookingTableViewController, Stage primaryStage) {
        this.vehicleService = vehicleService;
        this.primaryStage = primaryStage;
        this.bookingController = bookingController;
        this.bookingTableViewController = bookingTableViewController;
    }

    public BookingTableViewController getBookingTableViewController() {
        return bookingTableViewController;
    }

    public void setBookingTableViewController(BookingTableViewController bookingTableViewController) {
        this.bookingTableViewController = bookingTableViewController;
    }

    @FXML
    private TextField nameDetailView;

    @FXML
    private TextField buildyearDetailView;

    @FXML
    private TextField descriptionDetailView;

    @FXML
    private TextField seatsDetailView;

    @FXML
    private TextField licenseRequirementsDetailView;

    @FXML
    private TextField licenseplateDetailView;

    @FXML
    private TextField powerSourceDetailView;

    @FXML
    private TextField powerDetailView;

    @FXML
    private TextField hourlyRateDetailView;

    @FXML
    private CheckBox licenseCCheckBoxEdit;

    @FXML
    private CheckBox licenseBCheckBoxEdit;

    @FXML
    private CheckBox licenseACheckBoxEdit;

    @FXML
    private Label createTimeDetailView;

    @FXML
    private Label noPictureLabelDetailView;

    @FXML
    private RadioButton engineButtonEdit;

    @FXML
    private RadioButton muscleButtonEdit;

    @FXML
    private ImageView imageViewDetailView;

    @FXML
    private Button changePictureButtonDetailView;

    @FXML
    private Button backButtonDetailView;

    @FXML
    private Button saveButtonDetailView;

    @FXML
    private Button editButtonDetailView;

    @FXML
    private Button deleteButtonDetailView;

    @FXML
    private Button createBookingWithVehicleButton;

    @FXML
    private Button addToBookingButton;

    @FXML
    private Label edittimeLabelDetailView;




    private static final Path imagePath = Paths.get(System.getProperty("user.home"),"/.sepm/images");

    @FXML
    public void initialize(){
        this.muscleButtonEdit.selectedProperty().bindBidirectional(this.powerDetailView.disableProperty());
    }

    public void fill(Vehicle vehicle){
        this.vehicle = vehicle;
        nameDetailView.setText(vehicle.getName());
        nameDetailView.setEditable(false);
        buildyearDetailView.setText(vehicle.getBuildyear().toString());
        buildyearDetailView.setEditable(false);
        descriptionDetailView.setText(vehicle.getDescription());
        descriptionDetailView.setEditable(false);
        seatsDetailView.setText(vehicle.getSeats().toString());
        seatsDetailView.setEditable(false);
        if(vehicle.getLicenseType() == null) {
            licenseRequirementsDetailView.setText("None");
        } else {
            licenseRequirementsDetailView.setText(vehicle.getLicenseType().toString());
        }
        licenseRequirementsDetailView.setEditable(false);
        licenseplateDetailView.setText(vehicle.getLicenseplate());
        licenseplateDetailView.setEditable(false);
        powerSourceDetailView.setText(vehicle.getPowerSource().toString());
        powerSourceDetailView.setEditable(false);
        powerDetailView.setText(vehicle.getPower().toString());
        powerDetailView.setEditable(false);
        hourlyRateDetailView.setText(vehicle.getHourlyRateCents().toString());
        hourlyRateDetailView.setEditable(false);
        createTimeDetailView.setText(vehicle.getCreatetime().toString());
        if(vehicle.getPicture() != null) {
            String path = imagePath + "/" + vehicle.getPicture();
          //  File file = new File(path);
          //  boolean test = file.exists();
            Image image = new Image("file:" + path);
            imageViewDetailView.setImage(image);
            imageViewDetailView.setVisible(true);
            noPictureLabelDetailView.setVisible(false);
        } else {
            noPictureLabelDetailView.setVisible(true);
        }
        edittimeLabelDetailView.setText(vehicle.getEdittime().toString());

    }

    @FXML
    private void backToTableView(ActionEvent event) {
        fill(vehicle);
      changeToTableView();
    }

    private void changeToTableView(){
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(tableViewController) ? tableViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Vehicles");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Tableview couldn't be changed");
        }
    }

    @FXML
    private void editVehicle(ActionEvent event) {
        createBookingWithVehicleButton.setDisable(true);
        addToBookingButton.setDisable(true);
        editButtonDetailView.setDisable(true);
        deleteButtonDetailView.setDisable(true);
        nameDetailView.setDisable(false);
        nameDetailView.setEditable(true);
        buildyearDetailView.setDisable(false);
        buildyearDetailView.setEditable(true);
        descriptionDetailView.setDisable(false);
        descriptionDetailView.setEditable(true);
        seatsDetailView.setDisable(false);
        seatsDetailView.setEditable(true);
        licenseACheckBoxEdit.setVisible(true);
        licenseBCheckBoxEdit.setVisible(true);
        licenseCCheckBoxEdit.setVisible(true);
        licenseRequirementsDetailView.setVisible(false);
        licenseplateDetailView.setDisable(false);
        licenseplateDetailView.setEditable(true);
        powerSourceDetailView.setVisible(false);
        muscleButtonEdit.setVisible(true);
        engineButtonEdit.setVisible(true);
       // powerDetailView.setDisable(false);
        powerDetailView.setEditable(true);
        hourlyRateDetailView.setDisable(false);
        hourlyRateDetailView.setEditable(true);

        changePictureButtonDetailView.setDisable(false);
        saveButtonDetailView.setDisable(false);

        if(!vehicle.getLicenseType().isEmpty()) {
            if(vehicle.getLicenseType().contains(LicenseType.A)){
                licenseACheckBoxEdit.setSelected(true);
                engineButtonEdit.setSelected(true);
            }
            if(vehicle.getLicenseType().contains(LicenseType.B)){
                licenseBCheckBoxEdit.setSelected(true);
                engineButtonEdit.setSelected(true);
            }
            if(vehicle.getLicenseType().contains(LicenseType.C)){
                licenseCCheckBoxEdit.setSelected(true);
                engineButtonEdit.setSelected(true);
            }
        }


    }

    @FXML
    private void changePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserForPictures(fileChooser);
        picture = fileChooser.showOpenDialog(changePictureButtonDetailView.getScene().getWindow());
        Image image = new Image("file:" + picture.getAbsolutePath());
        imageViewDetailView.setImage(image);
    }

    private static void configureFileChooserForPictures(FileChooser fileChooser) {
        fileChooser.setTitle("Save Image of Vehicle");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        String currentName = nameDetailView.getText();
        Integer currentBuildyear = parseInt(buildyearDetailView.getText());
        String currentDescription = null;
        if (descriptionDetailView.getText() != null && !descriptionDetailView.getText().isEmpty()) {
            currentDescription = descriptionDetailView.getText();
        }
        Integer currentSeats = parseInt(seatsDetailView.getText());
        String currentLicensePlate = null;

        List<LicenseType> currentLicenseType = new ArrayList<>();
        if(licenseACheckBoxEdit.isSelected()) {
            currentLicenseType.add(LicenseType.A);
        }
        if (licenseBCheckBoxEdit.isSelected()){
            currentLicenseType.add(LicenseType.B);
        }
        if (licenseCCheckBoxEdit.isSelected()) {
            currentLicenseType.add(LicenseType.C);
        }
        if(!currentLicenseType.isEmpty()) {
            currentLicensePlate = licenseplateDetailView.getText();
        }

        Double currentPower = parseDouble(powerDetailView.getText());
        Integer currentHourlyRate = parseInt(hourlyRateDetailView.getText());
        PowerSource currentPowerSource = null;
        if(muscleButtonEdit.isSelected()) {
            currentPowerSource = PowerSource.MUSCLE;
            currentPower = null;
        }
        if(engineButtonEdit.isSelected()) {
            currentPowerSource = PowerSource.ENGINE;
        }

        Vehicle currentVehicle = new Vehicle(currentName, currentBuildyear, currentDescription, currentSeats,currentLicenseType, currentLicensePlate, currentPowerSource, currentPower, currentHourlyRate, vehicle.getCreatetime(), LocalDateTime.now());
        currentVehicle.setUUIDForEditing(vehicle.getUUIDForEditing());

        try {
            try {
                vehicleService.passEditedVehicleToPersistence(currentVehicle,picture,vehicle);
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
            buildAlert(INFORMATION, "Your vehicle was edited successfully!").showAndWait();
        } catch (InvalidVehicleException e) {
            LOG.error("Vehicle couldn't be passed to Service");
            buildAlert(ERROR,e.getConstraintViolations().stream().collect(joining("\n"))).showAndWait();
           // new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
        }
    }

    @FXML
    private void deleteButtonDetailViewClicked(ActionEvent event) {
        try {
            vehicleService.deleteVehicleFromPersistence(vehicle);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        changeToTableView();
    }

    @FXML
    private void createBookingWithCurrentVehicle(ActionEvent event) {
        List<Vehicle> currentVehicleList = new ArrayList<>();
        currentVehicleList.add(vehicle);
        bookingController.setVehicleList(currentVehicleList);

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingController) ? bookingController : null);

        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("New Booking");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Booking couldn't be changed");
        }
    }

    @FXML
    private void addVehicleToExistingBooking(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/booking_tableview.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(bookingTableViewController) ? bookingTableViewController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            bookingTableViewController.changeToAddToBookingMode(vehicle);
            primaryStage.setTitle("Bookings");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage for Booking Tableview couldn't be changed",e);
        }

    }




    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TableViewController getTableViewController() {
        return tableViewController;
    }

    public void setTableViewController(TableViewController tableViewController) {
        this.tableViewController = tableViewController;
    }
}

