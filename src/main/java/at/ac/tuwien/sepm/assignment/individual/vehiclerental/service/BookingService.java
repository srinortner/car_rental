package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * service layer of booking, connects ui with dao
 */

public interface BookingService {

    /**
     * validates booking and passes it to persistence
     * @param booking that needs to be passed to persistence
     * @return booking that was added to database
     * @throws InvalidBookingException if the validation fails
     */
    Booking addBookingToPersistence(Booking booking) throws InvalidBookingException;

    /**
     * passes vehicle, booking and licensense information to persistence layer
     * @param vehicle that needs to be passed to persistence
     * @param booking that needs to be passed to persistence
     * @param license information that needs to be passed to persistence
     */
    void addLicenseInformationToPersistence (Vehicle vehicle, Booking booking, License license) throws ServiceException;

    /**
     * calls method for getting all bookings in database
     * @return list of all bookings in database as returned from persistence layer
     */
    List<Booking> getAllBookingsFromPersistence() throws ServiceException;

    /**
     * calls method to finish booking in database
     * @param booking which needs to be finished
     */
    void finishBookingInPersistence(Booking booking) throws ServiceException;

    /**
     * checks booking status, calculates cancellation fee and calls method for changing status to canceled in persistence
     * @param booking that needs to be canceled
     * @throws ServiceException if changing of the status in persistence fails
     * @throws InvalidBookingException if validating of booking status fails
     */
    void cancelBookingInPersistence(Booking booking) throws ServiceException, InvalidBookingException;

    /**
     * calls method for getting ids of vehicles contained in booking in persistence
     * @param booking that ids of vehicles it contains are needed
     * @return list of ids as returned form persistence
     */
    List<Long> getVehicleIDsFromPersistence(Booking booking) throws ServiceException;

    /**
     * gets all previous versions from vehicle and checks if any version is between or partially between currentStartTime and currentEndTime
     * @param vehicle that availability needs to be returned
     * @param currentStartTime of availability interval
     * @param currentEndTime of availability interval
     * @return true if none of the versions of the vehicle is booked in that time
     */
    boolean checkAvailiabilityOfVehicle (Vehicle vehicle, LocalDateTime currentStartTime, LocalDateTime currentEndTime);

    /**
     * calls method for updating bookings in persistence
     * @param booking that needs to be updated
     */
    void updateBookingInPersistence(Booking booking) throws ServiceException;

    /**
     * calls method for getting booking by ID in persistence
     * @param id of booking that needs to be fetched from persistence
     * @return booking returned by persistence
     */
    Booking getBookingByIDFromPersistence (Long id);

    /**
     * validates booking before adding it to existing booking
     * @param vehicle  that needs to be added to booking
     * @param booking  vehicle needs to be added to
     * @throws InvalidBookingException if the validation fails
     */
    void validateAddingVehicleToExistingBooking(Vehicle vehicle, Booking booking) throws InvalidBookingException;

    /**
     * calculates current total price and calling method for updating it in persistence
     * @param booking of which
     */
    void updateTotalPrice(Booking booking) throws ServiceException;

    /**
     * calls method for getting bookings in time interval from persistence
     * @param startTime of interval
     * @param endTime of interval
     * @return list returned by persistence
     */
    List<Booking> getBookingsInTimeInterval(LocalDateTime startTime, LocalDateTime endTime) throws ServiceException;


}
