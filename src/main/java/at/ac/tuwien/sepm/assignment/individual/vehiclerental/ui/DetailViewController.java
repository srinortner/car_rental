package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleVehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DetailViewController {


    private VehicleService vehicleService;

    private File picture = null;
    private Vehicle vehicle = null;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public DetailViewController(VehicleService vehicleService) {

        this.vehicleService = vehicleService;
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
    private Label createTimeDetailView;

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


    private static final Path imagePath = Paths.get(System.getProperty("user.home"),"/.sepm/images");

    private void initialize() {

        changePictureButtonDetailView.setVisible(false);
        saveButtonDetailView.setVisible(false);

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
        licenseRequirementsDetailView.setText(vehicle.getLicenseType().toString());
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
        Image image = new Image(imagePath + "/" + vehicle.getPicture());
        imageViewDetailView.setImage(image);
    }

    @FXML
    private void backToTableView(ActionEvent event) {

    }

    @FXML
    void editVehicle(ActionEvent event) {
        nameDetailView.setText(vehicle.getName());
        nameDetailView.setEditable(true);
        buildyearDetailView.setText(vehicle.getBuildyear().toString());
        buildyearDetailView.setEditable(true);
        descriptionDetailView.setText(vehicle.getDescription());
        descriptionDetailView.setEditable(true);
        seatsDetailView.setText(vehicle.getSeats().toString());
        seatsDetailView.setEditable(true);
        licenseRequirementsDetailView.setText(vehicle.getLicenseType().toString());
        licenseRequirementsDetailView.setEditable(true);
        licenseplateDetailView.setText(vehicle.getLicenseplate());
        licenseplateDetailView.setEditable(true);
        powerSourceDetailView.setText(vehicle.getPowerSource().toString());
        powerSourceDetailView.setEditable(true);
        powerDetailView.setText(vehicle.getPower().toString());
        powerDetailView.setEditable(true);
        hourlyRateDetailView.setText(vehicle.getHourlyRateCents().toString());
        hourlyRateDetailView.setEditable(true);

        changePictureButtonDetailView.setVisible(true);
        saveButtonDetailView.setVisible(true);
    }

    @FXML
    private void changePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserForPictures(fileChooser);
        picture = fileChooser.showOpenDialog(changePictureButtonDetailView.getScene().getWindow());
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
        try {
            vehicleService.addVehicleToPersistence(vehicle,picture);
        } catch (InvalidVehicleException e) {
            LOG.error("The vehicle you are trying to add is invalid!");
        } catch (IOException e) {
            LOG.error("Vehicle couldn't be added to persistence!");
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}

