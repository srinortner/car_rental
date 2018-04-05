package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class StatisticsController {

    @FXML
    private LineChart<String, Integer> turnoverLineChart;
    @FXML
    private CategoryAxis xAxisLineChart;

    @FXML
    private NumberAxis yAxisLineChart;

    @FXML
    private BarChart<?, ?> numberOfBookingsBarChart;

    @FXML
    private CategoryAxis xAxisBarChart;

    @FXML
    private NumberAxis yAxisBarChart;

    @FXML
    private DatePicker fromDatePickerTurnover;

    @FXML
    private DatePicker toDatePickerTurnover;

    @FXML
    private CheckBox LicenseCheckBoxTurnoverA;

    @FXML
    private CheckBox LicenseCheckBoxTurnoverB;

    @FXML
    private CheckBox LicenseCheckBoxTurnoverC;

    @FXML
    private DatePicker fromDatePickerNumberOfBookings;

    @FXML
    private DatePicker toDatePickerNumberOfBookings;

    @FXML
    private CheckBox licenseCheckboxANumberOfBookings;

    @FXML
    private CheckBox licenseCheckboxBNumberOfBookings;

    @FXML
    private CheckBox licenseCheckboxCNumberOfBookings;

    @FXML
    private Button generateTurnoverButton;

    @FXML
    private Button generateNumberOfBookingsButton;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxisLineChart.setCategories(monthNames);
    }

    @FXML
    void createNumberOfBookingsChart(ActionEvent event) {

    }

    @FXML
    void generateTurnoverChart(ActionEvent event) {

    }

}

