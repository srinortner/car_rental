package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class VehicleController {

    private Vehicle currentVehicle;
    private VehicleService currentService;

    public VehicleController( VehicleService currentService) {
        this.currentService = currentService;
    }

    @FXML
    private TextField addVehicleName;

    @FXML
    private TextField addVehicleBuildyear;

    @FXML
    private TextField addVehicleDescription;

    @FXML
    private TextField addVehicleSeats;

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
    void saveVehicle(ActionEvent event) {
        String currentName = addVehicleName.getText();
        int currentBuildyear = Integer.parseInt(addVehicleBuildyear.getText());
        String currentDescription = null;
        if(addVehicleDescription.getText() != null) {
            currentDescription = addVehicleDescription.getText();
        }
        int currentSeats = Integer.parseInt(addVehicleSeats.getText());
        String currentLicensePlate = addVehicleLicenseplate.getText();
        boolean currentHasEngine = false;
        if(addVehicleEngine.isSelected()) {
            currentHasEngine = true;
        }
        double currentPower = Double.parseDouble(addVehiclePower.getText());
        int currentHourlyRate = Integer.parseInt(addVehicleHourlyRate.getText());

        // public Vehicle(String name, int buildyear, String description, int seats, String licenseplate, boolean hasEngine, double power, int hourlyRateCents)
        currentVehicle = new Vehicle(currentName,currentBuildyear,currentDescription,currentSeats,currentLicensePlate,currentHasEngine,currentPower,currentHourlyRate);
        currentVehicle.setCreatetime(LocalDateTime.now());
        currentService.addVehicleToPersistence(currentVehicle);

    }

}
