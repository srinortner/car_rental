package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleDAO {

    Vehicle addVehicleToDatabase(Vehicle vehicle);

    List<Vehicle> getAllVehiclesFromDatabase();

    void editVehicle(Vehicle newVehicle, Vehicle oldVehicle);

    void deleteVehicleFromDatabase(Vehicle vehicle);

    Vehicle getVehicleByID(Long id);

    List<Vehicle> searchVehicles(List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String Name, PowerSource powerSource, Integer seats);
}
