package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimpleVehicleDAO implements VehicleDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());
    private Connection connection = DBConnection.getConnection();
    private boolean editing = false;


    @Override
    public Vehicle addVehicleToDatabase(Vehicle vehicle) {
        if (vehicle == null) {
            LOG.warn("Vehicle in addVehicleToDatabase is null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO VEHICLE VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT )");
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setInt(2, vehicle.getBuildyear());
            preparedStatement.setString(3, vehicle.getDescription());
            preparedStatement.setInt(4, vehicle.getSeats());
            preparedStatement.setString(5, vehicle.getLicenseplate());
            preparedStatement.setString(6, vehicle.getPowerSource().toString());
            if (vehicle.getPower() == null) {
                preparedStatement.setNull(7, Types.NULL);
            } else {
                preparedStatement.setDouble(7, vehicle.getPower());
            }
            preparedStatement.setInt(8, vehicle.getHourlyRateCents());
            preparedStatement.setString(9, vehicle.getPicture());
            preparedStatement.setTimestamp(10, Timestamp.valueOf(vehicle.getCreatetime()));
            preparedStatement.setString(11, vehicle.getUUIDForEditing());
            preparedStatement.setTimestamp(12, Timestamp.valueOf(vehicle.getEdittime()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            vehicle.setId(resultSet.getLong(1));

            resultSet.close();
            preparedStatement.close();
            editing = false;
            LOG.info("Vehicle added to database.");

        } catch (SQLException e) {
            LOG.error("Vehicle couldn't be added to database!");
        }

        int numberOfLicenseRequirements = 0;
        if (!isNull(vehicle.getLicenseType().size())) {
            numberOfLicenseRequirements = vehicle.getLicenseType().size();
        }
        while (numberOfLicenseRequirements > 0) {
            LicenseType lt = vehicle.getLicenseType().get(numberOfLicenseRequirements - 1);
            addLicenseRequirementsToDatabase(vehicle.getId(), lt);
            numberOfLicenseRequirements--;
        }
        return vehicle;
    }

    public void addLicenseRequirementsToDatabase(Long id, LicenseType licenseType) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO vehicle_license_requirement VALUES (?,?)");
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, licenseType.toString());
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

    public void editVehicle(Vehicle newVehicle, Vehicle oldVehicle) {

        if (oldVehicle == null) {
            LOG.warn("Vehicle in addVehicleToDatabase is null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE VEHICLE SET DELETED = ? WHERE ID = ? ");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setLong(2, oldVehicle.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            editing = true;
            LOG.info("Vehicle is updated!");
        } catch (SQLException e) {
            LOG.error("Vehicle couldn't be updated!");
        }

        addVehicleToDatabase(newVehicle);

    }

    //TODO: Führerscheine auslesen
    public List<Vehicle> getAllVehiclesFromDatabase() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicleList = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT id,name,BUILDYEAR,DESCRIPTION,SEATS,LICENSEPLATE,TYPE,POWER,hourlyrate,PICTURE,CREATETIME,uuid_for_editing, edittime FROM VEHICLE WHERE DELETED = ?;");
            preparedStatement.setBoolean(1, false);
            resultSet = preparedStatement.executeQuery();
            vehicleList = getDataFromResultSet(resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOG.error("Error while loading data from database!");
        }
        return vehicleList;
    }

    public List<Vehicle> getDataFromResultSet(ResultSet resultSet) {
        List<Vehicle> currentVehicleList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Long currentID = resultSet.getLong(1);
                String currentName = resultSet.getString(2);
                Integer currentYear = resultSet.getInt(3);
                String currentDescription = resultSet.getString(4);
                Integer currentSeats = resultSet.getInt(5);
                String currentLicenseplate = resultSet.getString(6);
                String currentType = resultSet.getString(7);
                Double currentPower = resultSet.getDouble(8);
                Integer currentHourlyRate = resultSet.getInt(9);
                String currentPicture = resultSet.getString(10);
                Timestamp currentCreateTime = resultSet.getTimestamp(11);
                String currentUuidForEditing = resultSet.getString(12);
                Timestamp currentEdittime = resultSet.getTimestamp(13);

                PowerSource type = PowerSource.MUSCLE;
                if (currentType.equals("ENGINE")) {
                    type = PowerSource.ENGINE;
                } else {
                    type = PowerSource.MUSCLE;
                }
                LocalDateTime time = currentCreateTime.toLocalDateTime();
                LocalDateTime edittime = currentEdittime.toLocalDateTime();

                Vehicle currentVehicle = new Vehicle(currentID, currentName, currentYear, currentDescription, currentSeats, currentLicenseplate, type, currentPower, currentHourlyRate, currentPicture, time, edittime);
                currentVehicle.setUUIDForEditing(currentUuidForEditing);
                currentVehicleList.add(currentVehicle);
            }
        } catch (SQLException e) {
            LOG.error("Error while getting data from resultSet");
        }

        return currentVehicleList;
    }
}
