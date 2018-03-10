package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;

import java.io.File;

public interface VehicleService {

    Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws IllegalVehicleException;

    String addPicture(File file);
}
