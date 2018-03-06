package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Validator {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected boolean validateProduct(Vehicle vehicle) {
        if(vehicle == null) {
            LOG.warn("{} is null!", vehicle);
            return false;
        }
        if(vehicle.getName().isEmpty() || vehicle.getName().equals(null)) {
            LOG.warn("Name of {} is null!", vehicle);
            return false;
        }
        if(vehicle.getBuildyear() == 0) {
            LOG.warn("Buildyear of {} is 0.", vehicle);
            return false;
        }
        return true;
    }
}
