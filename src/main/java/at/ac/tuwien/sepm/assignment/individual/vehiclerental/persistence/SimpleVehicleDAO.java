package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.util.Objects.isNull;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class SimpleVehicleDAO implements VehicleDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());
    private Connection connection = DBConnection.getConnection();

    @Override
    public Vehicle addVehicleToDatabase(Vehicle vehicle) {
        if(vehicle == null) {
            LOG.warn("Vehicle in addVehicleToDatabase is null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO VEHICLE VALUES(DEFAULT,?,?,?,?,?,?,?,?,?, ?,DEFAULT )");
            preparedStatement.setString(1,vehicle.getName());
            preparedStatement.setInt(2,vehicle.getBuildyear());
            preparedStatement.setString(3,vehicle.getDescription());
            preparedStatement.setInt(4,vehicle.getSeats());
            preparedStatement.setString(5,vehicle.getLicenseplate());
            preparedStatement.setString(6,vehicle.getPowerSource().toString());
            if(vehicle.getPower() == null) {
                preparedStatement.setNull(7, Types.NULL);
            } else {
                preparedStatement.setDouble(7,vehicle.getPower());
            }
            preparedStatement.setInt(8,vehicle.getHourlyRateCents());
            preparedStatement.setString(9,vehicle.getPicture());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(vehicle.getCreatetime()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            vehicle.setId(resultSet.getLong(1));

            resultSet.close();
            preparedStatement.close();

            LOG.info("Vehicle added to database.");

        } catch (SQLException e) {
            LOG.error("Vehicle couldn't be added to database!");
        }

        int numberOfLicenseRequirements = 0;
        if(!isNull(vehicle.getLicenseType().size())){
           numberOfLicenseRequirements = vehicle.getLicenseType().size();
        }
        while (numberOfLicenseRequirements > 0) {
            LicenseType lt = vehicle.getLicenseType().get(numberOfLicenseRequirements-1);
            addLicenseRequirementsToDatabase(vehicle.getId(),lt);
            numberOfLicenseRequirements--;
        }
        return vehicle;
    }

    public void addLicenseRequirementsToDatabase(Long id, LicenseType licenseType) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO vehicle_license_requirement VALUES (?,?)");
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2,licenseType.toString());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            resultSet.close();
            preparedStatement.close();

            LOG.info("License Requirement added to Database!");

        } catch (SQLException e) {
            LOG.error("License Requirement couldn't be added to database!");
        }
    }
}
