package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking addBookingToPersistence(Booking booking) throws InvalidBookingException;

    void addLicenseInformationToPersistence (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate);

    List<Booking> getBookingsForVehicleFromPersistence(Long vehicleID);

    List<Booking> getAllBookingsFromPersistence();
}
