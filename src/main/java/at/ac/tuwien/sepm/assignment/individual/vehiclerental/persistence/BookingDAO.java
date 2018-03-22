package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;

import java.time.LocalDate;

public interface BookingDAO {

    Booking addBookingToDatabase (Booking booking);

    void addLicenseToDatabase (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate);
}
