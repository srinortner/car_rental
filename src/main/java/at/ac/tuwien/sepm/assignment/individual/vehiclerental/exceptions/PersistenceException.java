package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

public class PersistenceException extends Exception{

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
