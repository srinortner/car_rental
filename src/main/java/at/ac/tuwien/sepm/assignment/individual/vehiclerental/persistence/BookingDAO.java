package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

    Booking addBookingToDatabase (Booking booking) throws PersistenceException;

    void addLicenseToDatabase (Long vehicleId, Long bookingId, License license);
    List<Booking> getAllBookingsOfVehicle (Long id);
    List<Booking> getAllBookingsFromDatabase();
    void finishBooking(Booking booking);
    void cancelBooking(Booking booking);
    List<Long> getVehicleIDsFromDatabase(Booking booking);
    Booking getBookingByID (Long id) throws PersistenceException;
    void updateBookingInDatabase(Booking booking);

}
