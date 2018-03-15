package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface VehicleService {

    Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws InvalidVehicleException, IOException;

    String addPicture(File file) throws IOException;

    List<Vehicle> getListOfVehiclesFromPersistence();

}
