package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;

import java.io.File;
import java.io.IOException;

public interface VehicleService {

    Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws IllegalVehicleException, IOException;

    String addPicture(File file) throws IOException;


}
