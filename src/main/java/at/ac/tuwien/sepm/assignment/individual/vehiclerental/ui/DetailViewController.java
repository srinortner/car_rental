package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;


import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.h2.store.fs.FilePath;
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

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseDouble;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;

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
        //TODO: find out why picture is not shown
        if(vehicle.getPicture() != null) {
            String path = imagePath + "/" + vehicle.getPicture();
            File file = new File(path);
            Image image = new Image("file:" + path);
            imageViewDetailView.setImage(image);
        //    imageViewDetailView.setFitHeight(200);
          //  imageViewDetailView.setFitWidth(150);
            imageViewDetailView.setVisible(true);
            noPictureLabelDetailView.setVisible(false);
        } else {
            noPictureLabelDetailView.setVisible(true);
        }

    }

    @FXML
    private void backToTableView(ActionEvent event) {

    }

    @FXML
    void editVehicle(ActionEvent event) {
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
        //TODO: Validieren
        powerSourceDetailView.setEditable(true);
        powerDetailView.setDisable(false);
        powerDetailView.setEditable(true);
        hourlyRateDetailView.setDisable(false);
        hourlyRateDetailView.setEditable(true);

        changePictureButtonDetailView.setDisable(false);
        saveButtonDetailView.setDisable(false);
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
        PowerSource powerSource = null;
        if(powerSourceDetailView.equals("ENGINE")){
            powerSource = PowerSource.ENGINE;
        } else {
            powerSource = PowerSource.MUSCLE;
        }

        Vehicle currentVehicle = new Vehicle(currentName, currentBuildyear, currentDescription, currentSeats,currentLicenseType, currentLicensePlate, powerSource, currentPower, currentHourlyRate, vehicle.getCreatetime());

        try {
            vehicleService.passEditedVehicleToPersistence(currentVehicle,null,vehicle);
        } catch (InvalidVehicleException e) {
            LOG.error("Vehicle couldn't be passed to Service");
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}

