package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidVehicleException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.DBConnection;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.SimpleVehicleDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleVehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleServiceTest {

    private VehicleDAO vehicleDAO = new SimpleVehicleDAO();
    private VehicleService vehicleService = new SimpleVehicleService(vehicleDAO);

    public VehicleServiceTest () throws PersistenceException{}

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
        try {
            vehicleService.addVehicleToPersistence(vehicle,null);
        } catch (InvalidVehicleException | IOException e) {
            e.printStackTrace();
        }
        long id = 4;
        vehicle.setId(id);
        try {
            Assert.assertEquals(vehicle, vehicleDAO.getById(id));
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InvalidVehicleException.class)
    public void createInvalidVehicle() throws PersistenceException, IOException, InvalidVehicleException {
        vehicleService.addVehicleToPersistence(null,null);
    }
}
