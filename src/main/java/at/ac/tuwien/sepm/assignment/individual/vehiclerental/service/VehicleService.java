package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {

    Vehicle addVehicleToPersistence(Vehicle vehicle, File file) throws InvalidVehicleException, IOException;

    String addPicture(File file) throws IOException;

    List<Vehicle> getListOfVehiclesFromPersistence();

    void passEditedVehicleToPersistence(Vehicle newVehicle, File picture, Vehicle oldVehicle) throws InvalidVehicleException;

    void deleteVehicleFromPersistence(Vehicle vehicle);

    Vehicle getVehiclesByIDFromPersistence(Long id);

    List<Vehicle> searchForVehiclesInPersistence (List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String Name, PowerSource powerSource, Integer seats);

    List<Vehicle> getAllLegacyVehicles(Vehicle vehicle);

    void setBookingService(BookingService bookingService);
}
