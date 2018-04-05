package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Booking addBookingToPersistence(Booking booking) throws InvalidBookingException;

    void addLicenseInformationToPersistence (Vehicle vehicle, Booking booking, License license);

    List<Booking> getAllBookingsFromPersistence();

    void finishBookingInPersistence(Booking booking);

    void cancelBookingInPersistence(Booking booking) throws ServiceException, InvalidBookingException;

    List<Long> getVehicleIDsFromPersistence(Booking booking);

    boolean checkAvailiabilityOfVehicle (Vehicle vehicle, LocalDateTime currentStartTime, LocalDateTime currentEndTime);

    void updateBookingInPersistence(Booking booking);

    Booking getBookingByIDFromPersistence (Long id);


}
