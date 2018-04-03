package at.ac.tuwien.sepm.assignment.individual.vehiclerental.util;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

import static javafx.scene.control.ButtonType.OK;

public final class AlertFactory {

    private AlertFactory() {
    }

    public static Alert buildAlert(Alert.AlertType error, String collect) {
        Alert alert = new Alert(error, collect, OK);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500,300);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert;
    }
}
