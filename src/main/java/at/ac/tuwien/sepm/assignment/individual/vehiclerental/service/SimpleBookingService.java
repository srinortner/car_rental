package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateBooking;

public class SimpleBookingService implements BookingService{

    private BookingDAO bookingDAO;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleBookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public Booking addBookingToPersistence(Booking booking) {
       validateBooking(booking);
       return bookingDAO.addBookingToDatabase(booking);
    }
}
