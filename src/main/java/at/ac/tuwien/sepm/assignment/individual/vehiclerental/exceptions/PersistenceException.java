package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

/**
 * Is thrown if operation in persistence layer fails
 */

public class PersistenceException extends Exception{

    public PersistenceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
