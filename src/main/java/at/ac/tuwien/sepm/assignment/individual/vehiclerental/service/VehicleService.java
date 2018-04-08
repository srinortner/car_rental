package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * service layer for vehicles, connects vehicle DAO with vehicle UI
 */

public interface VehicleService {


    /**
     * validates vehicle and calls method for adding it to database in persistence
     * @param vehicle that needs to be added to database
     * @param file that needs to be added to database
     * @return that was added to database
     * @throws InvalidVehicleException if validating of vehicle fails
     * @throws IOException if adding picture fails
     */
    Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws InvalidVehicleException, IOException, ServiceException;

    /**
     * adds picture to persistence, validates picture
     * @param file picture that needs to be added to vehicle
     * @return picture name
     * @throws IOException if copying of file fails
     */
    String addPicture(File file) throws IOException;

    /**
     * calls method for getting all vehicles from persistence
     * @return list of vehicles returned from database
     */
    List<Vehicle> getListOfVehiclesFromPersistence();

    /**
     * passes edited vehicle to persistence layer
     * @param newVehicle vehicle after editing
     * @param picture picture of vehicle
     * @param oldVehicle vehicle after editing
     * @throws InvalidVehicleException if validating of vehicle fails
     */
    void passEditedVehicleToPersistence(Vehicle newVehicle, File picture, Vehicle oldVehicle) throws InvalidVehicleException, ServiceException;

    /**
     * calls method in persistence to delete vehicle from database
     * @param vehicle that needs to be deleted
     */
    void deleteVehicleFromPersistence(Vehicle vehicle) throws ServiceException;

    /**
     * gets vehicle from persistence by id
     * @param id of vehicle to get
     * @return vehicle with given id from persistence
     */
    Vehicle getVehiclesByIDFromPersistence(Long id) throws ServiceException;

    /**
     * pass filter criteria to persistence and check availability of each vehicle returned
     * @param licenseTypes license requirements
     * @param hourlyPriceMin minimal hourly price
     * @param hourlyPriceMax maximal hourly price
     * @param startTime beginning of time interval
     * @param endTime end of time interval
     * @param Name of vehicle
     * @param powerSource vehicle needs to use
     * @param seats vehicle needs to have
     * @return list of vehicles that meet al criteria
     */
    List<Vehicle> searchForVehiclesInPersistence (List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String Name, PowerSource powerSource, Integer seats);

    /**
     * calls method for getting all versions of vehicle from database
     * @param vehicle of which versions are needed
     * @return list of all vehicle versions
     */
    List<Vehicle> getAllLegacyVehicles(Vehicle vehicle);

    /**
     * setter for booking service
     * @param bookingService booking service
     */
    void setBookingService(BookingService bookingService);
}
