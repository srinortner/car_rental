package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleDAO {

    Vehicle addVehicleToDatabase(Vehicle vehicle) throws PersistenceException;

    List<Vehicle> getAll() throws PersistenceException;

    void editVehicle(Vehicle newVehicle, Vehicle oldVehicle) throws PersistenceException;

    void deleteVehicleFromDatabase(Vehicle vehicle);

    Vehicle getById(Long id) throws PersistenceException;

    List<Vehicle> search(List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String Name, PowerSource powerSource, Integer seats) throws PersistenceException;

    List<Vehicle> getAllVehiclesByUUID(String uuidForEditing) throws PersistenceException;
}
