package at.ac.tuwien.sepm.assignment.individual.vehiclerental.util;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class Validator {

    private Validator() {
        // intentionally empty cause Validator is a Utility class
    }

    //TODO: Kennzeichen wenn Fuehrerschein, Leistung wenn motorisiert, und Motor check

    public static void validateVehicle(Vehicle vehicle) throws InvalidVehicleException {
        List<String> constraintViolations = new ArrayList<>();
        if (vehicle == null) {
            constraintViolations.add("Vehicle must not be null");
        } else {
            if (isNull(vehicle.getName()) || vehicle.getName().isEmpty()) {
                constraintViolations.add("Name must not be empty!");
            }
            if (isNull(vehicle.getBuildyear()) || vehicle.getBuildyear() <= 0) {
                constraintViolations.add("Buildyear must be a valid integer greater than zero!");
            }
            if (isNull(vehicle.getHourlyRateCents()) || vehicle.getHourlyRateCents() < 0) {
                constraintViolations.add("Hourly price must be a valid number greater than zero!");
            }
        }
        if (!constraintViolations.isEmpty()) {
            throw new InvalidVehicleException(constraintViolations);
        }
    }
}
