package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.PowerSource;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimpleVehicleDAO implements VehicleDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private Connection connection = DBConnection.getConnection();
    private boolean editing = false;


    @Override
    public Vehicle addVehicleToDatabase(Vehicle vehicle) throws PersistenceException{
        if (vehicle == null) {
            LOG.warn("Vehicle in addVehicleToDatabase is null!");
            throw new IllegalArgumentException("Vehicle in addVehicleToDatabase is null!!!!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO VEHICLE VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT )");
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setInt(2, vehicle.getBuildyear());
            preparedStatement.setString(3, vehicle.getDescription());
            if(vehicle.getSeats() == null) {
                preparedStatement.setString(4,null);
            } else {
                preparedStatement.setInt(4, vehicle.getSeats());
            }
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
            throw new PersistenceException("Vehicle couldn't be added to database!", e);
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

    public void addLicenseRequirementsToDatabase(Long id, LicenseType licenseType) throws PersistenceException {
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
            LOG.error("License Requirement couldn't be added to database!",e);
            throw new PersistenceException(e.getMessage(),e);
        }
    }

    public List<LicenseType> getLicenseRequirements(Long id) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LicenseType> licenseList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT LICENSE FROM VEHICLE_LICENSE_REQUIREMENT WHERE VEHICLE_ID = ?");
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            licenseList = getLicenseRequirementsFromResultSet(resultSet);
        } catch (SQLException e) {
           LOG.error("License requirements couldn't be loaded from database!");
            throw new PersistenceException("Vehicle couldn't be added to database!", e);
        }

        return licenseList;
    }

    public List<LicenseType> getLicenseRequirementsFromResultSet(ResultSet resultSet) throws PersistenceException {
        List<LicenseType> currentlist = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String currentLicense = resultSet.getString(1);
                if(currentLicense.equals("A")){
                    currentlist.add(LicenseType.A);
                }
                if(currentLicense.equals("B")){
                    currentlist.add(LicenseType.B);
                }
                if(currentLicense.equals("C")){
                    currentlist.add(LicenseType.C);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error while trying to load license requirements from reslutSet");
            throw new PersistenceException(e.getMessage(),e);
        }
        return currentlist;
    }

    public void editVehicle(Vehicle newVehicle, Vehicle oldVehicle) throws PersistenceException {

        deleteVehicleFromDatabase(oldVehicle);
        addVehicleToDatabase(newVehicle);

    }


    public void deleteVehicleFromDatabase(Vehicle vehicle) throws PersistenceException {
        if (vehicle == null) {
            LOG.warn("Vehicle in addVehicleToDatabase is null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE VEHICLE SET DELETED = ? WHERE ID = ? ");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setLong(2, vehicle.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            editing = true;
            LOG.info("Vehicle is updated!");
        } catch (SQLException e) {
            LOG.error("Vehicle couldn't be updated!",e);
            throw new PersistenceException(e.getMessage(),e);
        }
    }


    public List<Vehicle> getAll() throws PersistenceException {
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
            LOG.error("Error while loading data from database!",e);
            throw new PersistenceException(e.getMessage(),e);
        }
        return vehicleList;
    }

    private List<Vehicle> getDataFromResultSet(ResultSet resultSet) throws PersistenceException {
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
                List<LicenseType> licenseList = getLicenseRequirements(currentID);
                Vehicle currentVehicle = new Vehicle(currentID, currentName, currentYear, currentDescription, currentSeats, currentLicenseplate, type, currentPower, currentHourlyRate, currentPicture, time, edittime);
                currentVehicle.setUUIDForEditing(currentUuidForEditing);
                currentVehicle.setLicenseType(licenseList);
                currentVehicleList.add(currentVehicle);
            }
        } catch (SQLException e) {
            LOG.error("Error while getting data from resultSet");
            throw new PersistenceException("Error while getting data from resultSet", e);
        }

        return currentVehicleList;
    }

    public Vehicle getById(Long id) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vehicle vehicle = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT id,name,BUILDYEAR,DESCRIPTION,SEATS,LICENSEPLATE,TYPE,POWER,hourlyrate,PICTURE,CREATETIME,uuid_for_editing, edittime FROM VEHICLE WHERE ID = ?;");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            vehicle = getVehicleByIDFromResultSet(resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOG.error("Error while loading data from database!");
            throw new PersistenceException("Error while loading data from database!", e);
        }
        return vehicle;
    }

    private Vehicle getVehicleByIDFromResultSet(ResultSet resultSet) throws PersistenceException {
        Vehicle currentVehicle = null;

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
                List<LicenseType> licenseList = getLicenseRequirements(currentID);
                currentVehicle = new Vehicle(currentID, currentName, currentYear, currentDescription, currentSeats, currentLicenseplate, type, currentPower, currentHourlyRate, currentPicture, time, edittime);
                currentVehicle.setUUIDForEditing(currentUuidForEditing);
                currentVehicle.setLicenseType(licenseList);

            }
        } catch (SQLException e) {
            LOG.error("Error while getting data from resultSet");
            throw new PersistenceException(e.getMessage(),e);
        }

        return currentVehicle;

    }

    public List<Vehicle> search(List<LicenseType> licenseTypes, Integer hourlyPriceMin, Integer hourlyPriceMax, LocalDateTime startTime, LocalDateTime endTime, String name, PowerSource powerSource, Integer seats) throws PersistenceException {
        String query = "SELECT id,name,BUILDYEAR,DESCRIPTION,SEATS,LICENSEPLATE,TYPE,POWER,hourlyrate,PICTURE,CREATETIME,uuid_for_editing, edittime FROM VEHICLE WHERE DELETED = FALSE";

        if(!(hourlyPriceMin == null)){
            query += " AND hourlyrate >= " + hourlyPriceMin.toString();
        }
        if(!(hourlyPriceMax == null)) {
            query += " AND hourlyrate <= " + hourlyPriceMax.toString();
        }
        if(!(name == null)) {
            query += " AND name LIKE '%" + name + "%'";
        }
        if(!(powerSource == null)) {
            if(powerSource.equals(PowerSource.MUSCLE)){
                query += " AND type = 'MUSCLE'";
            } else if (powerSource.equals(PowerSource.ENGINE)) {
                query += " AND type = 'ENGINE'";
            }
        }
        if(!(seats == null)){
            query += " AND seats = " + seats;
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vehicle> searchResults = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            searchResults = getDataFromResultSet(resultSet);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            LOG.error("Search in Database didn't work!");
        }
        if(!licenseTypes.isEmpty()) {
            List<Vehicle> temp = new ArrayList<>();
            for (Vehicle vehicle : searchResults) {
                List<LicenseType> licenseRequirements = getLicenseRequirements(vehicle.getId());
                if (licenseRequirements.size() == 0) {
                    temp.add(vehicle);
                }
                for (LicenseType licenseType : licenseTypes) {
                    if (licenseRequirements.contains(licenseType) && !temp.contains(vehicle)) {
                        temp.add(vehicle);
                    }
                }
            }
            searchResults = temp;
        }


        return searchResults;
    }

    @Override
    public List<Vehicle> getAllVehiclesByUUID(String uuidForEditing) throws PersistenceException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicleList = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT id,name,BUILDYEAR,DESCRIPTION,SEATS,LICENSEPLATE,TYPE,POWER,hourlyrate,PICTURE,CREATETIME,uuid_for_editing, edittime FROM VEHICLE WHERE uuid_for_editing = ?;");
            preparedStatement.setString(1, uuidForEditing);
            resultSet = preparedStatement.executeQuery();
            vehicleList = getDataFromResultSet(resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            LOG.error("Error while loading data from database!");
            throw new PersistenceException("Error while loading data from database!", e);
        }
        return vehicleList;
    }

}
