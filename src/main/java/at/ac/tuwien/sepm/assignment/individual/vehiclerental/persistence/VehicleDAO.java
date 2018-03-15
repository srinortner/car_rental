package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;

import java.util.List;

public interface VehicleDAO {

    Vehicle addVehicleToDatabase(Vehicle vehicle);

    public List<Vehicle> getAllVehiclesFromDatabase();
}
