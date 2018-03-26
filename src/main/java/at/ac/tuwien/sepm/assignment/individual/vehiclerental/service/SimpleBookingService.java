package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Book;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateBooking;

public class SimpleBookingService implements BookingService{

    private BookingDAO bookingDAO;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleBookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public Booking addBookingToPersistence(Booking booking) throws InvalidBookingException {
       validateBooking(booking);
       return bookingDAO.addBookingToDatabase(booking);
    }

    public void addLicenseInformationToPersistence (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate) {
        bookingDAO.addLicenseToDatabase(vehicleId,bookingId,licensenumber,licensedate);
    }

    public List<Booking> getBookingsForVehicleFromPersistence(Long vehicleID){
        List<Booking> bookingList = bookingDAO.getAllBookingsOfVehicle(vehicleID);
        return bookingList;
    }

    public List<Booking> getAllBookingsFromPersistence(){
        return bookingDAO.getAllBookingsFromDatabase();
    }

    public void finishBookingInPersistence(Booking booking){
        bookingDAO.finishBooking(booking);
    }
}
