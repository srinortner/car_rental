package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.universe.service.UniverseService;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class UniverseController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final double VISIBLE = 1.0;
    private static final double INVISIBLE = 0.0;

    private final FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1));
    private final FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(3));

    private final UniverseService universeService;

    @FXML
    private Button btnQuestion;

    @FXML
    private Label lblAnswer;

    public UniverseController(UniverseService universeService) {
        this.universeService = universeService;
        // some transitions for a visually appealing effect
        fadeOutTransition.setFromValue(VISIBLE);
        fadeOutTransition.setToValue(INVISIBLE);
        fadeInTransition.setFromValue(INVISIBLE);
        fadeInTransition.setToValue(VISIBLE);
    }

    @FXML
    private void calculateAnswerButtonPressed() {
        LOG.trace("called calculateAnswerButtonPressed");
        // create a thread and task to prevent ui from freezing on when doing long running operations
        new Thread(new Task<String>() {
            @Override
            protected String call() {
                return universeService.calculateAnswer();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                LOG.debug("calculation succeeded with value {}", getValue());
                // update ui with animation
                lblAnswer.setText(getValue());
                fadeOutTransition.setNode(btnQuestion);
                fadeInTransition.setNode(lblAnswer);
                new ParallelTransition(
                    fadeOutTransition,
                    fadeInTransition
                ).playFromStart();
            }
        }, "calculation").start();
    }

}
