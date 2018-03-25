package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //method called, gets all Bookings of a certain vehicle from the database
    public List<Booking> getAllBookingsOfVehicle (Long id) {
        List<Booking> bookingsOfVehicle = new ArrayList<>();
        List<Long> bookingIDs = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT BOOKING_ID FROM VEHICLE_BOOKING WHERE VEHICLE_ID = ?");
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            bookingIDs = getBookingIDsFromResultSet(resultSet);

            for (Long bookingId:bookingIDs) {
                Booking currentBooking = getBookingByID(bookingId);
                bookingsOfVehicle.add(currentBooking);
            }
        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }

        return bookingsOfVehicle;
    }

    //Gets a booking by its ID
    private Booking getBookingByID (Long id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Booking currentBooking = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT ID, NAME, STARTDATE, ENDDATE, VEHICLES, TOTAL_PRICE, TYPE, createtime  FROM BOOKING WHERE ID = ?");
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            currentBooking = getDataFromResultSet(resultSet);

        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }

        return currentBooking;
    }


    //gets all IDs from the resultset
    private List<Long> getBookingIDsFromResultSet (ResultSet resultSet){
        List<Long> currentlist = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long currentBookingID = resultSet.getLong(1);
                currentlist.add(currentBookingID);
            }
        } catch (SQLException e) {
            LOG.error("Error while trying to load license requirements from reslutSet");
        }
        return currentlist;
    }

    //gets Bookings from a ResultSet
    public Booking getDataFromResultSet(ResultSet resultSet) {
        List<Booking> currentlist = new ArrayList<>();
        Booking currentBooking = null;

        try {
            while (resultSet.next()) {
                Long currentID = resultSet.getLong(1);
                String currentName = resultSet.getString(2);
                String currentPaymentNumber = resultSet.getString(3);
                Timestamp currentStartdate = resultSet.getTimestamp(4);
                Timestamp currentEnddate = resultSet.getTimestamp(5);
                String currentVehicles = resultSet.getString(6);
                Integer currentTotalPrice = resultSet.getInt(7);
                String currentType = resultSet.getString(8);
                Timestamp currentCreateTime = resultSet.getTimestamp(9);

                BookingStatus bookingStatus = BookingStatus.BOOKED;
                if(currentType.equals("BOOKED")){
                    bookingStatus = BookingStatus.BOOKED;
                }
                if(currentType.equals("PAID")){
                    bookingStatus = BookingStatus.PAID;
                }
                if(currentType.equals("CANCELED")){
                    bookingStatus = BookingStatus.CANCELED;
                }

                LocalDateTime start = currentStartdate.toLocalDateTime();
                LocalDateTime end = currentEnddate.toLocalDateTime();
               // Long id,String name, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, String bookedVehicles, Integer totalPrice, BookingStatus status, Timestamp createtime)
                currentBooking = new Booking(currentID, currentName,currentPaymentNumber,start,end,currentVehicles,currentTotalPrice, bookingStatus, currentCreateTime);
                currentlist.add(currentBooking);
            }
        } catch (SQLException e) {
            LOG.error("Error while trying to load license requirements from reslutSet");
        }
        return currentBooking;
    }

    public List<Booking> getAllBookingsFromDatabase(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Booking> currentList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT ID, NAME, PAYMENTNUMBER, STARTDATE, ENDDATE, TOTAL_PRICE, TYPE, createtime  FROM BOOKING");
            resultSet = preparedStatement.executeQuery();
            currentList = getAllDataFromResultSet(resultSet);

        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }

        return currentList;
    }

    private List<Booking> getAllDataFromResultSet(ResultSet resultSet) {
        List<Booking> currentList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Long currentID = resultSet.getLong(1);
                String currentName = resultSet.getString(2);
                String currentPaymentnumber = resultSet.getString(3);
                Timestamp currentStartDate = resultSet.getTimestamp(4);
                Timestamp currentEndDate = resultSet.getTimestamp(5);
                Integer currentTotalPrice = resultSet.getInt(6);
                String currentType = resultSet.getString(7);
                Timestamp currentCreateTime = resultSet.getTimestamp(8);

                LocalDateTime startDate = currentStartDate.toLocalDateTime();
                LocalDateTime endDate = currentEndDate.toLocalDateTime();


                BookingStatus bookingStatus = BookingStatus.BOOKED;
                if(currentType.equals("BOOKED")){
                    bookingStatus = BookingStatus.BOOKED;
                }
                if(currentType.equals("PAID")){
                    bookingStatus = BookingStatus.PAID;
                }
                if(currentType.equals("CANCELED")){
                    bookingStatus = BookingStatus.CANCELED;
                }

                //                                  Long id,String name, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, String bookedVehicles, Integer totalPrice, BookingStatus status, Timestamp createtime)
                Booking currentBooking = new Booking(currentID,currentName,currentPaymentnumber,startDate,endDate," ",currentTotalPrice, bookingStatus,currentCreateTime);
                currentList.add(currentBooking);


            }
        } catch (SQLException e) {
            LOG.error("Error while getting data from resultSet");
        }
        return currentList;
    }
}
