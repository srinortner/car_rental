package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;

public interface VehicleService {

    Vehicle addVehicleToPersistence(Vehicle vehicle) throws IllegalVehicleException;
}
