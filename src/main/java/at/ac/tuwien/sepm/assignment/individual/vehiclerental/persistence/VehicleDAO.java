package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;

import java.util.List;

public interface VehicleDAO {

    Vehicle addVehicleToDatabase(Vehicle vehicle);

    List<Vehicle> getAllVehiclesFromDatabase();

    void editVehicle(Vehicle newVehicle, Vehicle oldVehicle);

    void deleteVehicleFromDatabase(Vehicle vehicle);

    Vehicle getVehicleByID(Long id);
}
