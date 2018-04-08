package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO for bookings
 */

public interface BookingDAO {

    /**
     * adds new entry in booking table in database
     * @param booking booking that should be added to database
     * @return added Booking
     * @throws PersistenceException
     */
    Booking addBookingToDatabase (Booking booking) throws PersistenceException;

    /**
     * adds new entry for each license the person booking has in vehicle_booking table of database
     * @param vehicle vehicle that license needs to be saved for
     * @param booking of which the license needs to be saved
     * @param license the license that needs to be saved
     */
    void addLicenseToDatabase (Vehicle vehicle, Booking booking, License license) throws PersistenceException;

    /**
     * returns all bookings that contain given vehicle
     * @param vehicle vehicle of which bookings shall be returned
     * @return List of bookings of vehicle
     */
    List<Booking> getAllBookingsOfVehicle (Vehicle vehicle) throws PersistenceException;

    /**
     * Gets all entries of booking table
     * @return all saved bookings
     */
    List<Booking> getAllBookingsFromDatabase() throws PersistenceException;

    /**
     * sets status of given booking to paid, updates entry in booking table
     * @param booking which needs to change status
     */
    void finishBooking(Booking booking) throws PersistenceException;

    /**
     * sets status of given booking to canceled, updates entry in booking table
     * @param booking which needs to change status
     * @throws PersistenceException is thrown when updating fails
     * @throws InvalidBookingException is thrown if booking is already finished
     */
    void cancelBooking(Booking booking) throws PersistenceException, InvalidBookingException;

    /**
     * gets IDs of vehicles contained in booking from vehicle_booking table
     * @param booking of which vehicle ids shall be returned
     * @return list of ids
     */
    List<Long> getVehicleIDsFromDatabase(Booking booking) throws PersistenceException;

    /**
     * gets one booking by it's ID from booking table
     * @param id of booking that shall be returned
     * @return booking with given ID
     * @throws PersistenceException is thrown when booking couldn't be fetched from database
     */
    Booking getBookingByID (Long id) throws PersistenceException;

    /**
     * updates booking after editing
     * @param booking that needs to be updated
     */
    void updateBookingInDatabase(Booking booking) throws PersistenceException;

    /**
     * updates total price of booking after editing
     * @param booking of which the price needs to be updated
     */
    void updateTotalPriceInDatabase(Booking booking) throws PersistenceException;

    /**
     * gets all bookings in a certain time interval for statistics
     * @param starttime of interval
     * @param endtime of interval
     * @return List of bookings that are in between or partially in  between starttime and enddtime
     */
    List<Booking> getBookingsInTimeIntervalFromDatabase(LocalDateTime starttime, LocalDateTime endtime) throws PersistenceException;

}
