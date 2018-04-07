package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidVehicleException extends Exception{

    private final List<String> constraintViolations;

    public InvalidVehicleException(List<String> constraintViolations) {
        super();
        this.constraintViolations=constraintViolations;
    }

    public List<String> getConstraintViolations() {
        return constraintViolations;
    }

    @Override
    public String getMessage() {
        return constraintViolations.stream().collect(Collectors.joining("; "));
    }
}
