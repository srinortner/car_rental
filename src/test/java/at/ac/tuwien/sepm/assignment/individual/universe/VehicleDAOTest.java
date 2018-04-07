package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.DBConnection;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.SimpleVehicleDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOTest {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VehicleDAO vehicleDAO = new SimpleVehicleDAO();


    @BeforeClass
    public static void beforeClass() {
        DBConnection.setTestmode(true);
    }

    @AfterClass
    public static void afterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void createValidVehicle() {
        //String name, Integer buildyear, String description, Integer seats, List<LicenseType> licenseType, String licenseplate, PowerSource powerSource, Double power, Integer hourlyRateCents, LocalDateTime createtime, LocalDateTime edittime
        List<LicenseType> licenseTypeList = new ArrayList<>();
        licenseTypeList.add(LicenseType.B);
        Vehicle vehicle = new Vehicle("VW", 2009, "This is a car", 4, licenseTypeList, "VB12345", PowerSource.ENGINE, 44.12, 200, LocalDateTime.now(), LocalDateTime.now());
        LOG.debug("Vehicle test {}", vehicle);
        LOG.debug("vehicle dao {}", vehicleDAO);
        try {
            vehicleDAO.addVehicleToDatabase(vehicle);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        long id = 4;
        vehicle.setId(id);
        try {
            Vehicle vehicleByID = vehicleDAO.getById(id);
            Vehicle vehicle1 = vehicleByID;
            LOG.debug("Vehicle by ID {}", vehicle1);
            Assert.assertEquals(vehicle, vehicleByID);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidVehicle() throws PersistenceException {
        vehicleDAO.addVehicleToDatabase(null);
    }

}

