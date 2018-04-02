package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.PaymentType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.DBConnection;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.SimpleBookingDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.SimpleVehicleDAO;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

public class BookingDAOTest {

    private BookingDAO bookingDAO = new SimpleBookingDAO();

    @BeforeClass
    public static void beforeClass() {
        DBConnection.setTestmode(true);
    }

    @AfterClass
    public static void afterClass() {
        DBConnection.closeConnection();
    }

    @Test
    public void createValidBooking() {
        //String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status, LocalDateTime createtime
        LocalDateTime start = LocalDateTime.of(2018, 5, 5, 1, 0);
        LocalDateTime end = LocalDateTime.of(2018, 5, 6, 1, 0);
        Vehicle vehicle = new Vehicle("VW", null, null, null, null, null, null, null, null, null, null);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        Booking booking = new Booking("Brock", PaymentType.CREDITCARD, "4532642579652817", start, end, vehicleList, 1000, BookingStatus.BOOKED, LocalDateTime.now());
        Long id = Long.valueOf(3);
        booking.setId(id);

        try {
            bookingDAO.addBookingToDatabase(booking);
        } catch (PersistenceException e) {
            fail();
        }

        try {
            Assert.assertEquals(booking, bookingDAO.getBookingByID(id));
        } catch (PersistenceException e) {
            fail();
        }
    }

        @Test(expected = PersistenceException.class)
        public void createInvalidVehicle() throws PersistenceException{
            bookingDAO.addBookingToDatabase(null);
        }

    }


