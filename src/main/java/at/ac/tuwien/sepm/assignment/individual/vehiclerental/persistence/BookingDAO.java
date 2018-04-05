package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

    Booking addBookingToDatabase (Booking booking) throws PersistenceException;

    void addLicenseToDatabase (Vehicle vehicle, Booking booking, License license);
    List<Booking> getAllBookingsOfVehicle (Vehicle vehicle);
    List<Booking> getAllBookingsFromDatabase();
    void finishBooking(Booking booking);
    void cancelBooking(Booking booking) throws PersistenceException, InvalidBookingException;
    List<Long> getVehicleIDsFromDatabase(Booking booking);
    Booking getBookingByID (Long id) throws PersistenceException;
    void updateBookingInDatabase(Booking booking);
    void updateTotalPriceInDatabase(Booking booking);

}
