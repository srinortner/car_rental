package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Is thrown when validating of booking information fails
 */

public class InvalidBookingException extends Exception{
    private final List<String> constraintViolations;

    public InvalidBookingException(List<String> constraintViolations) {
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
