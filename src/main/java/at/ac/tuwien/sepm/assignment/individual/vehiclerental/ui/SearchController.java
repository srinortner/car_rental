package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidSearchInputException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.SpinnerFactory.buildSpinner;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateSearchInputs;
import static java.util.stream.Collectors.joining;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class SearchController {

    @FXML
    private TextField nameOfVehicleSearch;

    @FXML
    private TextField hourlypriceMin;

    @FXML
    private TextField hourlypriceMax;

    @FXML
    private TextField seatsSearch;

    @FXML
    private DatePicker fromDatePickerSearch;

    @FXML
    private Spinner<Integer> fromHourPickerSearch;

    @FXML
    private Spinner<Integer> fromMinutePickerSearch;

    @FXML
    private CheckBox licenseTypeACheckBoxSearch;

    @FXML
    private CheckBox licenseTypeBCheckBoxSearch;

    @FXML
    private CheckBox licenseTypeCCheckBoxSearch;

    @FXML
    private DatePicker toDatePickerSearch;

    @FXML
    private Spinner<Integer> toHourPickerSearch;

    @FXML
    private Spinner<Integer> toMinutePickerSearch;

    @FXML
    private RadioButton radioButtonEngineSearch;

    @FXML
    private RadioButton radioButtonMuscleSearch;

    @FXML
    private Button searchButton;

    private VehicleService currentService;

    private BookingService bookingService;

    private boolean searchButtonClicked = false;
    private List<Vehicle> foundVehicles = null;

    public List<Vehicle> getFoundVehicles() {
        return foundVehicles;
    }

    public void setFoundVehicles(List<Vehicle> foundVehicles) {
        this.foundVehicles = foundVehicles;
    }

    public boolean isSearchButtonClicked() {
        return searchButtonClicked;
    }

    public void setSearchButtonClicked(boolean searchButtonClicked) {
        this.searchButtonClicked = searchButtonClicked;
    }

    public SearchController(VehicleService currentService, BookingService bookingService) {
        this.currentService = currentService;
        this.bookingService = bookingService;
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactoryHour = buildSpinner(23);
        SpinnerValueFactory<Integer> valueFactoryHourTo = buildSpinner(23);

        valueFactoryHour.setValue(1);
        valueFactoryHourTo.setValue(1);
        fromHourPickerSearch.setValueFactory(valueFactoryHour);
        toHourPickerSearch.setValueFactory(valueFactoryHourTo);

        SpinnerValueFactory<Integer> valueFactoryMin = buildSpinner(59);
        SpinnerValueFactory<Integer> valueFactoryMinTo = buildSpinner(59);

        valueFactoryMin.setValue(0);
        valueFactoryMinTo.setValue(0);
        fromMinutePickerSearch.setValueFactory(valueFactoryMin);
        toMinutePickerSearch.setValueFactory(valueFactoryMinTo);

    }

    @FXML
    private void searchForVehicles(ActionEvent event) {
        searchButtonClicked = true;
        String currentName = nameOfVehicleSearch.getText();
        String currentHPMinString = hourlypriceMin.getText();
        String currentHPMaxString = hourlypriceMax.getText();
        String currentSeatsString = seatsSearch.getText();

        LocalDateTime currentStartTime = null;
        if (!(fromDatePickerSearch.getValue() == null)) {
            currentStartTime = LocalDateTime.of(fromDatePickerSearch.getValue(), LocalTime.of(fromHourPickerSearch.getValue(), fromMinutePickerSearch.getValue()));
        }

        LocalDateTime currentEndTime = null;
        if (!(toDatePickerSearch.getValue() == null)) {
            currentEndTime = LocalDateTime.of(toDatePickerSearch.getValue(), LocalTime.of(toHourPickerSearch.getValue(), toMinutePickerSearch.getValue()));
        }

        try {
            validateSearchInputs(currentHPMinString, currentHPMaxString, currentStartTime, currentEndTime, currentSeatsString);


            Integer currentHourlyPriceMin = parseInt(hourlypriceMin.getText());
            Integer currentHourlyPriceMax = parseInt(hourlypriceMax.getText());
            Integer currentSeats = parseInt(seatsSearch.getText());

            List<LicenseType> licenses = new ArrayList<>();
            if (licenseTypeACheckBoxSearch.isSelected()) {
                licenses.add(LicenseType.A);
            }
            if (licenseTypeBCheckBoxSearch.isSelected()) {
                licenses.add(LicenseType.B);
            }
            if (licenseTypeCCheckBoxSearch.isSelected()) {
                licenses.add(LicenseType.C);
            }
            PowerSource currentPowerSource = null;
            if (radioButtonEngineSearch.isSelected() && !radioButtonMuscleSearch.isSelected()) {
                currentPowerSource = PowerSource.ENGINE;
            }
            if (radioButtonMuscleSearch.isSelected() && !radioButtonEngineSearch.isSelected()) {
                currentPowerSource = PowerSource.MUSCLE;
            }
            if (radioButtonMuscleSearch.isSelected() && radioButtonEngineSearch.isSelected()) {
                currentPowerSource = PowerSource.ANY;
            }

            foundVehicles = currentService.searchForVehiclesInPersistence(licenses, currentHourlyPriceMin, currentHourlyPriceMax, currentStartTime, currentEndTime, currentName, currentPowerSource, currentSeats);

        } catch (InvalidSearchInputException e) {
            buildAlert(ERROR, e.getConstraintViolations().stream().collect(joining("\n"))).showAndWait();
        }
        ((Stage) searchButton.getScene().getWindow()).close();

    }



}
