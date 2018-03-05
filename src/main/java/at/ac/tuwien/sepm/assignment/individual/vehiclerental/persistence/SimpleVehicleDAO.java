package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            preparedStatement = connection.prepareStatement("INSERT INTO VEHICLE VALUES(DEFAULT,?,?,?,?,?,?,?,?,? ,DEFAULT )");
            preparedStatement.setString(1,vehicle.getName());
            preparedStatement.setInt(2,vehicle.getBuildyear());
            preparedStatement.setString(3,vehicle.getDescription());
            preparedStatement.setInt(4,vehicle.getSeats());
            preparedStatement.setString(5,vehicle.getLicenseplate());
            preparedStatement.setString(6,vehicle.isHasEngine() ? "Engine" : "Muscle");
            preparedStatement.setDouble(7,vehicle.getPower());
            preparedStatement.setInt(8,vehicle.getHourlyRateCents());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(vehicle.getCreatetime()));
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
        return vehicle;
    }
}
