package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class SimpleVehicleService  implements VehicleService{

    private VehicleDAO vehicleDAO;

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleVehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public Vehicle addVehicleToPersistence(Vehicle vehicle) {
        if(vehicle != null){
            vehicleDAO.addVehicleToDatabase(vehicle);
            LOG.debug("Vehicle was added to pesistence.");
        }
        return vehicle;
    }
}
