package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Parser.parseInt;

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


    public SearchController(VehicleService currentService, BookingService bookingService) {
        this.currentService = currentService;
        this.bookingService = bookingService;
    }

    @FXML
    private void searchForVehicles(ActionEvent event) {
        String currentName = nameOfVehicleSearch.getText();
        Integer currentHourlyPriceMin = parseInt(hourlypriceMin.getText());
        Integer currentHourlyPriceMax = parseInt(hourlypriceMax.getText());
        Integer currentSeats = parseInt(seatsSearch.getText());
        LocalDateTime currentStartTime = null;
        if(!(fromDatePickerSearch.getValue() == null)){
            currentStartTime = LocalDateTime.of(fromDatePickerSearch.getValue(), LocalTime.of(fromHourPickerSearch.getValue(),fromMinutePickerSearch.getValue()));
        }

        LocalDateTime currentEndTime = null;
        if(!(toDatePickerSearch.getValue() == null)){
            currentEndTime = LocalDateTime.of(toDatePickerSearch.getValue(), LocalTime.of(toHourPickerSearch.getValue(),toMinutePickerSearch.getValue()));
        }

        List<LicenseType> licenses = new ArrayList<>();
        if(licenseTypeACheckBoxSearch.isSelected()) {
            licenses.add(LicenseType.A);
        }
        if(licenseTypeBCheckBoxSearch.isSelected()) {
            licenses.add(LicenseType.B);
        }
        if(licenseTypeCCheckBoxSearch.isSelected()) {
            licenses.add(LicenseType.C);
        }
        PowerSource currentPowerSource = null;
        if(radioButtonEngineSearch.isSelected() && !radioButtonMuscleSearch.isSelected() ) {
            currentPowerSource = PowerSource.ENGINE;
        }
        if(radioButtonMuscleSearch.isSelected() && !radioButtonEngineSearch.isSelected()) {
            currentPowerSource = PowerSource.MUSCLE;
        }
        if(radioButtonMuscleSearch.isSelected() && radioButtonEngineSearch.isSelected()) {
            currentPowerSource = PowerSource.ANY;
        }

        List<Vehicle> foundVehicles = currentService.searchForVehiclesInPersistence(licenses,currentHourlyPriceMin, currentHourlyPriceMax, currentStartTime, currentEndTime, currentName, currentPowerSource, currentSeats);
        int x = 0;

    }


}
