package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDate;

public class SimpleBookingDAO implements BookingDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());
    private Connection connection = DBConnection.getConnection();

    public SimpleBookingDAO() {

    }

    public Booking addBookingToDatabase (Booking booking) {
        if(booking == null) {
            LOG.warn("Booking is null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO BOOKING VALUES(DEFAULT,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,booking.getName());
            preparedStatement.setString(2,booking.getPaymentNumber());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(booking.getStartDate()));
            preparedStatement.setTimestamp(4,Timestamp.valueOf(booking.getEndDate()));
            if(booking.getBookedVehicles().equals(null)) {
                preparedStatement.setString(5,"NONE");
            } else {
                preparedStatement.setString(5, booking.getBookedVehicles().toString());
            }
            preparedStatement.setDouble(6,booking.getTotalPrice());
            preparedStatement.setString(7,booking.getStatus().toString());
            preparedStatement.setTimestamp(8,Timestamp.valueOf(booking.getCreatetime()));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            booking.setId(resultSet.getLong(1));

            resultSet.close();
            preparedStatement.close();

            LOG.info("Booking added to database!");

        } catch (SQLException e) {
            LOG.error("Booking couldn't be added to database!");
        }
        return booking;
    }

    public void addLicenseToDatabase (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate) {
        if(licensenumber == null || licensedate == null) {
            LOG.error("licensetype or licensedate are null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT  INTO VEHICLE_BOOKING VALUES (?,?,?,?)");
            preparedStatement.setLong(1,vehicleId);
            preparedStatement.setLong(2,bookingId);
            preparedStatement.setString(3,licensenumber);
            preparedStatement.setDate(4,Date.valueOf(licensedate));
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            resultSet.close();
            preparedStatement.close();

            LOG.info("Licensenumber and date added to database!");
        } catch (SQLException e) {
            LOG.error("Licensenumber and date couldn't be added to Database!");
        }
    }
}
