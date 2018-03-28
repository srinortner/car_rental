package at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence;

import at.ac.tuwien.sepm.assignment.individual.entities.*;
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
            preparedStatement = connection.prepareStatement("INSERT INTO BOOKING VALUES(DEFAULT,?,?,?,?,?,?,?,?,?, DEFAULT)");
            preparedStatement.setString(1,booking.getName());
            preparedStatement.setString(2,booking.getPaymentType().toString());
            preparedStatement.setString(3,booking.getPaymentNumber());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(booking.getStartDate()));
            preparedStatement.setTimestamp(5,Timestamp.valueOf(booking.getEndDate()));
            if(booking.getBookedVehicles().equals(null)) {
                preparedStatement.setString(6,"NONE");
            } else {
                preparedStatement.setString(6, booking.getBookedVehicles().toString());
            }
            preparedStatement.setDouble(7,booking.getTotalPrice());
            preparedStatement.setString(8,booking.getStatus().toString());
            preparedStatement.setTimestamp(9,Timestamp.valueOf(booking.getCreatetime()));
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

    public void addLicenseToDatabase (Long vehicleId, Long bookingId, String licensetype, String licensenumber, LocalDate licensedate) {
        if(licensenumber == null || licensedate == null) {
            LOG.error("licensetype or licensedate are null!");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT  INTO VEHICLE_BOOKING VALUES (?,?,?,?,?)");
            preparedStatement.setLong(1,vehicleId);
            preparedStatement.setLong(2,bookingId);
            preparedStatement.setString(3, licensetype);
            preparedStatement.setString(4,licensenumber);
            preparedStatement.setDate(5,Date.valueOf(licensedate));
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
            preparedStatement = connection.prepareStatement("SELECT ID, NAME,paymenttype,PAYMENTNUMBER, STARTDATE, ENDDATE, VEHICLES, TOTAL_PRICE, TYPE, createtime, paidtime  FROM BOOKING WHERE ID = ?");
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
                String currentPaymentType = resultSet.getString(3);
                String currentPaymentNumber = resultSet.getString(4);
                Timestamp currentStartdate = resultSet.getTimestamp(5);
                Timestamp currentEnddate = resultSet.getTimestamp(6);
                String currentVehicles = resultSet.getString(7);
                Integer currentTotalPrice = resultSet.getInt(8);
                String currentType = resultSet.getString(9);
                Timestamp currentCreateTime = resultSet.getTimestamp(10);
                Timestamp currentpaidtime = resultSet.getTimestamp(11);

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

                PaymentType paymentType = PaymentType.IBAN;
                if(currentPaymentType.equals("CREDITCARD")) {
                    paymentType = PaymentType.CREDITCARD;
                }
                LocalDateTime start = currentStartdate.toLocalDateTime();
                LocalDateTime end = currentEnddate.toLocalDateTime();
               // Long id,String name, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, String bookedVehicles, Integer totalPrice, BookingStatus status, Timestamp createtime)
                currentBooking = new Booking(currentID,currentName,paymentType,currentPaymentNumber,start,end,currentVehicles,currentTotalPrice, bookingStatus, currentCreateTime, currentpaidtime);
                currentlist.add(currentBooking);
            }
        } catch (SQLException e) {
            LOG.error("Error while trying to load license requirements from reslutSet");
        }
        return currentBooking;
    }

    //get all Bookings
    public List<Booking> getAllBookingsFromDatabase(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Booking> currentList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT ID, NAME, paymenttype, PAYMENTNUMBER, STARTDATE, ENDDATE, TOTAL_PRICE, TYPE, createtime, paidtime, ivoice_number_booking  FROM BOOKING");
            resultSet = preparedStatement.executeQuery();
            currentList = getAllDataFromResultSet(resultSet);

        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }

        return currentList;
    }

    //get data from all bookings from result set
    private List<Booking> getAllDataFromResultSet(ResultSet resultSet) {
        List<Booking> currentList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Long currentID = resultSet.getLong(1);
                String currentName = resultSet.getString(2);
                String currentPaymenttype = resultSet.getString(3);
                String currentPaymentnumber = resultSet.getString(4);
                Timestamp currentStartDate = resultSet.getTimestamp(5);
                Timestamp currentEndDate = resultSet.getTimestamp(6);
                Integer currentTotalPrice = resultSet.getInt(7);
                String currentType = resultSet.getString(8);
                Timestamp currentCreateTime = resultSet.getTimestamp(9);
                Timestamp currentpaidtime = resultSet.getTimestamp(10);
                Integer currentInvoiceNumber = resultSet.getInt(11);

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

                PaymentType paymentType = PaymentType.IBAN;
                if(currentPaymenttype.equals("CREDITCARD")) {
                    paymentType = PaymentType.CREDITCARD;
                }

                Booking currentBooking = new Booking(currentID,currentName,paymentType,currentPaymentnumber,startDate,endDate," ",currentTotalPrice, bookingStatus,currentCreateTime, currentpaidtime);
                currentList.add(currentBooking);
                currentBooking.setInvoiceNumber(currentInvoiceNumber);

                List<License> licensesOfBooking = getLicenseDataFromDatabase(currentBooking);
                List<LicenseType> licenseTypesOfPersonBooking = new ArrayList<>();
                for (License license: licensesOfBooking) {
                    if(license.getLicenseType().equals(LicenseType.A)) {
                        licenseTypesOfPersonBooking.add(LicenseType.A);
                        currentBooking.setLicensedateA(license.getLicenseDate());
                        currentBooking.setLicensenumberA(license.getLicenseNumber());
                    }
                    if(license.getLicenseType().equals(LicenseType.B)) {
                        licenseTypesOfPersonBooking.add(LicenseType.B);
                        currentBooking.setLicensedateB(license.getLicenseDate());
                        currentBooking.setLicensenumberB(license.getLicenseNumber());
                    }

                    if(license.getLicenseType().equals(LicenseType.C)) {
                        licenseTypesOfPersonBooking.add(LicenseType.C);
                        currentBooking.setLicensedateC(license.getLicenseDate());
                        currentBooking.setLicensenumberC(license.getLicenseNumber());
                    }
                }
                currentBooking.setPersonLicenseList(licenseTypesOfPersonBooking);
            }
        } catch (SQLException e) {
            LOG.error("Error while getting data from resultSet");
        }
        return currentList;
    }

    //update Booking to finished
    public void finishBooking(Booking booking){
        if(booking == null) {
            LOG.warn("Booking is null!");
        }

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE BOOKING SET TYPE = ?, PAIDTIME = ?, ivoice_number_booking = invoice_number.nextval WHERE ID = ?");
            preparedStatement.setString(1,"PAID");
            preparedStatement.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(3,booking.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            LOG.info("Booking was updated!");
        } catch (SQLException e) {
            LOG.error("Booking status couldn't be updated!");
        }
    }

    //update Booking to canceled
    public void cancelBooking(Booking booking){
        if(booking == null) {
            LOG.warn("Booking is null!");
        }
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE BOOKING SET TYPE = ?, TOTAL_PRICE = ?, PAIDTIME = ? ,ivoice_number_booking = invoice_number.nextval WHERE ID = ?");
            preparedStatement.setString(1, "CANCELED");
            preparedStatement.setInt(2,booking.getTotalPrice());
            booking.setPaidtime(Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(3,booking.getPaidtime());
            preparedStatement.setLong(4, booking.getId());
            preparedStatement.executeUpdate();



            preparedStatement.close();
            LOG.info("Booking was updated");
        } catch (SQLException e) {
            LOG.error("Booking status couldn't be updated");
        }


    }

   /* private void setIvoiceNumber(Booking booking) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE BOOKING SET  WHERE ID = ?");
            preparedStatement.setLong(1,booking.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    } */

    //gets all Licensensedata as well as vehicleIDs
    public List<License> getLicenseDataFromDatabase(Booking booking) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<License> licenseData = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT LICENSE, LICENSE_DATE, LICENSE_NUMBER  FROM VEHICLE_BOOKING WHERE BOOKING_ID = ?");
            preparedStatement.setLong(1,booking.getId());
            resultSet = preparedStatement.executeQuery();

            licenseData = getLicenseDataFromResultSet(resultSet);


        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }

        return licenseData;

    }

    private List<License> getLicenseDataFromResultSet (ResultSet resultSet) {
        List<License> currentData = new ArrayList<>();

        try{
            while (resultSet.next()) {
                String currentLicenseType = resultSet.getString(1);
                Timestamp currentLicenseDate = resultSet.getTimestamp(2);
                String currentLicenseNumber = resultSet.getString(3);

                LocalDateTime licensedatetime = currentLicenseDate.toLocalDateTime();
                LocalDate licensedate = LocalDate.of(licensedatetime.getYear(),licensedatetime.getMonth(),licensedatetime.getDayOfMonth());

                LicenseType type = LicenseType.C;
                if(currentLicenseType.equals("A")) {
                    type = LicenseType.A;
                }
                if(currentLicenseType.equals("B")){
                    type = LicenseType.B;
                }

                License currentLicense = new License(type,licensedate,currentLicenseNumber);
                currentData.add(currentLicense);
            }

        } catch (SQLException e) {
            LOG.error("Licensedata couldn't be loaded from database.");
        }

        return currentData;
    }

    public List<Long> getVehicleIDsFromDatabase(Booking booking) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Long> vehicleIDs = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT VEHICLE_ID FROM VEHICLE_BOOKING WHERE BOOKING_ID = ?");
            preparedStatement.setLong(1,booking.getId());
            resultSet = preparedStatement.executeQuery();
            vehicleIDs = getVehicleIDsFromResultSet(resultSet);

        } catch (SQLException e) {
            LOG.error("License requirements couldn't be loaded from database!");
        }
        return vehicleIDs;
    }

    public List<Long> getVehicleIDsFromResultSet(ResultSet resultSet) {
        List<Long> vehicleIDs = new ArrayList<>();

        try{
            while (resultSet.next()) {
               Long currentId = resultSet.getLong(1);
               vehicleIDs.add(currentId);
            }

        } catch (SQLException e) {
            LOG.error("Vehicle IDs couldn't be loaded from database.");
        }

        return vehicleIDs;
    }
}
