package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DetailViewController {

    private Vehicle vehicle;

    public DetailViewController(Vehicle vehicle) {
        this.vehicle = vehicle;
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
    private Button ChangePictureButtonDetailView;

    @FXML
    private Button backButtonDetailView;

    @FXML
    private Button saveButtonDetailView;

    private void initialize() {
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

    }

    @FXML
    private void backToTableView(ActionEvent event) {

    }

    @FXML
    private void changePicture(ActionEvent event) {

    }

    @FXML
    private void saveChanges(ActionEvent event) {

    }

}

