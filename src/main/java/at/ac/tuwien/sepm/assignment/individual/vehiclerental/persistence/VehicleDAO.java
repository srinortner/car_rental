package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO for vehicles
 */

public interface VehicleDAO {

    /**
     * creates new entry in vehicle table of database
     * @param vehicle which needs to be added to database
     * @return the vehicle added to the database
     * @throws PersistenceException if the new entry couldn't be created
     */
    Vehicle addVehicleToDatabase(Vehicle vehicle) throws PersistenceException;

    /**
     * gets all vehicles from database
     * @return a List of all saved vehicles
     * @throws PersistenceException if the vehicles couldn't be fetched from database
     */
    List<Vehicle> getAll() throws PersistenceException;

    /**
     * updates vehicle after editing in UI
     * @param newVehicle vehicle after editing
     * @param oldVehicle vehicle before editing
     * @throws PersistenceException if the vehicle can't be updated
     */
    void editVehicle(Vehicle newVehicle, Vehicle oldVehicle) throws PersistenceException;

    /**
     * sets deleted flag of entry in database true
     * @param vehicle which needs to be deleted
     */
    void deleteVehicleFromDatabase(Vehicle vehicle) throws PersistenceException;

    /**
     * gets one vehicle by its id from vehicle table
     * @param id of the vehicle that needs to be returned
     * @return vehicle with given id
     * @throws PersistenceException if the vehicle couldn't be fetched from database
     */
    Vehicle getById(Long id) throws PersistenceException;

    /**
     * filters the vehicle table by the given search criteria
     * @param licenseTypes license requirements
     * @param hourlyPriceMin minimal price per hour
     * @param hourlyPriceMax maximal price per hour
     * @param startTime of time interval
     * @param endTime of time interval
     * @param Name of vehicle
     * @param powerSource vehicle has to use
     * @param seats vehicle has to have
     * @return a list of vehicles meeting the search criteria
     * @throws PersistenceException if searching for vehicles fails
     */
    List<Vehicle> search(List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String Name, PowerSource powerSource, Integer seats) throws PersistenceException;

    /**
     * gets all vehicles with the same uuid from vehicle table, used to find deleted versions of vehicles
     * @param uuidForEditing of vehicle that previouse versions are needed of
     * @return list of vehicle and all its previous versions
     * @throws PersistenceException if the vehicles couldn't be fetched from database
     */
    List<Vehicle> getAllVehiclesByUUID(String uuidForEditing) throws PersistenceException;
}
