package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;

import java.time.LocalDate;

public interface BookingService {

    Booking addBookingToPersistence(Booking booking) throws InvalidBookingException;

    void addLicenseInformationToPersistence (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate);
}
