package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

public class ServiceException extends Exception{

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
