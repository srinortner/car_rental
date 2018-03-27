package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

    Booking addBookingToDatabase (Booking booking);

    void addLicenseToDatabase (Long vehicleId, Long bookingId,String licensetype, String licensenumber, LocalDate licensedate);
    List<Booking> getAllBookingsOfVehicle (Long id);
    List<Booking> getAllBookingsFromDatabase();
    void finishBooking(Booking booking);
    void cancelBooking(Booking booking);

}
