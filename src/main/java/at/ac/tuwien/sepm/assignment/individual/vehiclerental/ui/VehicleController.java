package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

public class VehicleController {

    private Vehicle currentVehicle;
    private VehicleService currentService;
    private String currentLicensePlate =null;
    private boolean currentEngine = false;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private TextField addVehicleName;

    @FXML
    private TextField addVehicleBuildyear;

    @FXML
    private TextField addVehicleDescription;

    @FXML
    private TextField addVehicleSeats;

    @FXML
    private CheckBox addVehicleLicenseA;

    @FXML
    private CheckBox addVehicleLicenseB;

    @FXML
    private Label addVehicleLicenseplateLabel;

    @FXML
    private Label addVehiclePowerLabel;

    @FXML
    private TextField addVehicleLicenseplate;

    @FXML
    private CheckBox addVehicleEngine;

    @FXML
    private CheckBox addVehicleMusclePower;

    @FXML
    private TextField addVehiclePower;

    @FXML
    private TextField addVehicleHourlyRate;

    @FXML
    private Button addVehicleBackButton;

    @FXML
    private Button addVehicleAddButton;

    @FXML
    private Button addVehicleAddPictureButton;

    private File picture = null;



    public VehicleController( VehicleService currentService) {
        this.currentService = currentService;

    }

/*    @FXML
    private void initialize() {
        addVehiclePower.textProperty().addListener(((observable, oldValue, newValue) -> {

        }));
    } */

    @FXML
    private void addVehicleActivateLicenseplateTextView(ActionEvent event) {
        if(addVehicleLicenseA.isSelected() || addVehicleLicenseB.isSelected()) {
            addVehicleLicenseplate.setVisible(true);
            addVehicleLicenseplateLabel.setOpacity(1);
            currentLicensePlate = addVehicleLicenseplate.getText();
        } else if (!addVehicleLicenseB.isSelected() && !addVehicleLicenseA.isSelected()){
            addVehicleLicenseplate.setVisible(false);
            addVehicleLicenseplateLabel.setOpacity(0.5);
            currentLicensePlate = null;
        }
    }

    @FXML
    private void addVehicleActivatePowerTextBox(ActionEvent event) {
        if(!currentEngine) {
            addVehiclePower.setVisible(true);
            addVehiclePowerLabel.setOpacity(1);
            addVehicleMusclePower.setSelected(false);
            addVehicleMusclePower.setIndeterminate(false);
            currentEngine = true;
        } else if(currentEngine) {
            addVehiclePower.setVisible(false);
            addVehiclePowerLabel.setOpacity(0.5);
            addVehicleEngine.setSelected(false);
            currentEngine = false;
        }
    }

    @FXML
    private void addPictureOfVehicle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserForPictures(fileChooser);
        picture = fileChooser.showOpenDialog(addVehicleAddPictureButton.getScene().getWindow());
       // File file = fileChooser.showSaveDialog(addVehicleAddPictureButton.getScene().getWindow());


    }

    private static void configureFileChooserForPictures (FileChooser fileChooser) {
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
    private void saveVehicle(ActionEvent event) {
        String currentName = addVehicleName.getText();
        int currentBuildyear = Integer.parseInt(addVehicleBuildyear.getText());
        String currentDescription = null;
        if(addVehicleDescription.getText() != null) {
            currentDescription = addVehicleDescription.getText();
        }
        int currentSeats = Integer.parseInt(addVehicleSeats.getText());
        double currentPower = Double.parseDouble(addVehiclePower.getText());
        int currentHourlyRate = Integer.parseInt(addVehicleHourlyRate.getText());

        // public Vehicle(String name, int buildyear, String description, int seats, String licenseplate, boolean hasEngine, double power, int hourlyRateCents)
        currentVehicle = new Vehicle(currentName,currentBuildyear,currentDescription,currentSeats,currentLicensePlate,currentEngine,currentPower,currentHourlyRate);
        currentVehicle.setCreatetime(LocalDateTime.now());
        try {
            currentService.addVehicleToPersistence(currentVehicle, picture);
        } catch (IllegalVehicleException e) {
            LOG.error("Vehicle couldn't be added to Persistence!");
        }

    }

}
