package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class SimpleBookingDAO implements BookingDAO{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());
    private Connection connection = DBConnection.getConnection();

    public SimpleBookingDAO(Connection connection) {
        this.connection = connection;
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
            preparedStatement.setDate(3, Date.valueOf(booking.getStartDate()));
            preparedStatement.setDate(4,Date.valueOf(booking.getEndDate()));
            preparedStatement.setString(5,booking.getBookedVehicles().toString());
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
}
