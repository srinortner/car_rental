package at.ac.tuwien.sepm.assignment.individual.vehiclerental.util;

import javafx.scene.control.SpinnerValueFactory;

public class SpinnerFactory {

    private SpinnerFactory() {
    }

    public static SpinnerValueFactory<Integer> buildSpinner(int maxSpinnerValue) {
        return new SpinnerValueFactory<>() {

            @Override
            public void decrement(int steps) {
                Integer current = this.getValue();
                if (current == 1) {
                    this.setValue(maxSpinnerValue);
                } else {
                    this.setValue(current - 1);
                }
            }

            @Override
            public void increment(int steps) {
                Integer current = this.getValue();
                if (current == maxSpinnerValue) {
                    this.setValue(1);
                } else {
                    this.setValue(current + 1);
                }
            }
        };
    }
}
