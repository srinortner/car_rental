package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseDouble;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;
import static java.util.stream.Collectors.joining;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.OK;

public class VehicleController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Vehicle currentVehicle;
    private VehicleService currentService;
    private String currentLicensePlate = null;
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
    private RadioButton rbEngine;

    @FXML
    private RadioButton rbMuscle;

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


    public VehicleController(VehicleService currentService) {
        this.currentService = currentService;

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

/*    @FXML
    private void initialize() {
        addVehiclePower.textProperty().addListener(((observable, oldValue, newValue) -> {

        }));
    } */

    @FXML
    private void initialize() {
        this.rbEngine.selectedProperty().bindBidirectional(this.addVehiclePower.visibleProperty());
        this.rbEngine.selectedProperty().addListener((radioButton, wasSelected, isSelected) -> {
            if(isSelected) {
                    addVehiclePowerLabel.setOpacity(1);
                } else {
                    addVehiclePower.clear();
                    addVehiclePowerLabel.setOpacity(0.5);
                }
        });
    }

    @FXML
    private void addVehicleActivateLicenseplateTextView(ActionEvent event) {
        if (addVehicleLicenseA.isSelected() || addVehicleLicenseB.isSelected()) {
            addVehicleLicenseplate.setVisible(true);
            addVehicleLicenseplateLabel.setOpacity(1);
            currentLicensePlate = addVehicleLicenseplate.getText();
        } else if (!addVehicleLicenseB.isSelected() && !addVehicleLicenseA.isSelected()) {
            addVehicleLicenseplate.setVisible(false);
            addVehicleLicenseplateLabel.setOpacity(0.5);
            currentLicensePlate = null;
        }
    }

    @FXML
    private void addPictureOfVehicle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserForPictures(fileChooser);
        picture = fileChooser.showOpenDialog(addVehicleAddPictureButton.getScene().getWindow());
        // File file = fileChooser.showSaveDialog(addVehicleAddPictureButton.getScene().getWindow());


    }

    @FXML
    private void saveVehicle(ActionEvent event) throws IOException {
        String currentName = addVehicleName.getText();
        Integer currentBuildyear = parseInt(addVehicleBuildyear.getText());
        String currentDescription = null;
        if (addVehicleDescription.getText() != null && !addVehicleDescription.getText().isEmpty()) {
            currentDescription = addVehicleDescription.getText();
        }
        Integer currentSeats = parseInt(addVehicleSeats.getText());
        Double currentPower = parseDouble(addVehiclePower.getText());
        Integer currentHourlyRate = parseInt(addVehicleHourlyRate.getText());

        // public Vehicle(String name, int buildyear, String description, int seats, String licenseplate, boolean hasEngine, double power, int hourlyRateCents)
        PowerSource powerSource = rbEngine.isSelected() ? PowerSource.ENGINE : PowerSource.MUSCLE;
        currentVehicle = new Vehicle(currentName, currentBuildyear, currentDescription, currentSeats, currentLicensePlate, powerSource, currentPower, currentHourlyRate, LocalDateTime.now());
        try {
            currentService.addVehicleToPersistence(currentVehicle, picture);
        } catch (InvalidVehicleException e) {
            LOG.error("Vehicle couldn't be added to Persistence! {}", e.getMessage());
            new Alert(ERROR, e.getConstraintViolations().stream().collect(joining("\n")), OK).showAndWait();
        }

    }

}
