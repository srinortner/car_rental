package at.ac.tuwien.sepm.assignment.individual.vehiclerental.ui;

import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.StatisticsService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static java.util.Comparator.comparing;
import static javafx.collections.FXCollections.observableArrayList;

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

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ObservableList<String> monthNames = observableArrayList();
    private ObservableList<String> dayNames = observableArrayList();

    private StatisticsService statisticsService = null;
    private IndexController indexController = null;
    private Stage primaryStage = null;

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    public StatisticsController(StatisticsService statisticsService, Stage primaryStage) {
        this.statisticsService = statisticsService;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        String[] days = {"Mo", "Tue", "We", "Thur", "Fri", "Sa", "Sun"};
        monthNames.addAll(Arrays.asList(months));
        dayNames.addAll(Arrays.asList(days));
        xAxisBarChart.setCategories(dayNames);
        xAxisLineChart.setTickLabelRotation(90);
    }

    @FXML
    private void createNumberOfBookingsChart(ActionEvent event) {

    }

    @FXML
    private void generateTurnoverChart(ActionEvent event) {
            LocalDateTime start = LocalDateTime.of(fromDatePickerTurnover.getValue(), LocalTime.of(0, 0));
            LocalDateTime end = LocalDateTime.of(toDatePickerTurnover.getValue(), LocalTime.of(23, 59));
            Map<LocalDate, Integer> dailyTurnovers = statisticsService.getDataForTurnover(start, end);
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            dailyTurnovers.entrySet()
                .stream()
                .sorted(comparing(Map.Entry::getKey))
                .map(e -> new XYChart.Data<>(e.getKey().toString(), e.getValue()))
                .forEach(e -> series.getData().add(e));
            series.setName("Daily Turnover");
            turnoverLineChart.setData(observableArrayList(series));
    }

    @FXML
    void changeToIndexView(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/index.fxml"));
        fxmlLoader.setControllerFactory(classToLoad -> classToLoad.isInstance(indexController) ? indexController : null);
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle("Bookings");
            primaryStage.show();

        } catch (IOException e) {
            LOG.error("Stage couldn't be changed", e);
        }
    }


}

