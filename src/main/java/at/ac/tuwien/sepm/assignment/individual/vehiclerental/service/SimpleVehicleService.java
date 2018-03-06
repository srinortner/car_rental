package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.IllegalVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class SimpleVehicleService  implements VehicleService{

    private VehicleDAO vehicleDAO;
    private Validator validator;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleVehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public Vehicle addVehicleToPersistence(Vehicle vehicle) throws IllegalVehicleException{
        if(validator.validateProduct(vehicle)){
            vehicleDAO.addVehicleToDatabase(vehicle);
            if(vehicle.getHourlyRateCents() < 0) {
                LOG.error("Hourly Price for {} is negative!", vehicle);
                throw new IllegalVehicleException("The vehicle entered is invalid!");
            }
            LOG.debug("Vehicle was added to pesistence.");
        } else {
            LOG.error("Vehicle is invalid.");
            throw new IllegalVehicleException("The vehicle entered is invalid!");
        }
        return vehicle;
    }
}
