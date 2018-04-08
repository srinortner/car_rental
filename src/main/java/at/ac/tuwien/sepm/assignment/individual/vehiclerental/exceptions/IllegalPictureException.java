package at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions;

import java.io.IOException;

/**
 * Is thrown when adding the picture if the picture doesn't meet the requirements of size or filetype
 */

public class IllegalPictureException extends IOException {
    public IllegalPictureException(String message) {
        super(message);
    }
}
