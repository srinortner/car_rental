package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;


/**
 * Is thrown if operation in service layer fails
 */

public class ServiceException extends Exception{

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
