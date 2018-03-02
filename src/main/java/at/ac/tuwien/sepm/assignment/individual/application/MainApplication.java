package at.ac.tuwien.sepm.assignment.individual.application;

import at.ac.tuwien.sepm.assignment.individual.universe.service.SimpleUniverseService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.UniverseService;
import at.ac.tuwien.sepm.assignment.individual.universe.ui.UniverseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public final class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setup application
        primaryStage.setTitle("Universe Calculator");
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        // initiate service and controller
        UniverseService universeService = new SimpleUniverseService();
        UniverseController universeController = new UniverseController(universeService);

        // prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/universe.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(universeController) ? universeController : null);
        primaryStage.setScene(new Scene(fxmlLoader.load()));

        // show application
        primaryStage.show();
        primaryStage.toFront();
        LOG.debug("Application startup complete");
    }

    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        Application.launch(MainApplication.class, args);
    }

}
