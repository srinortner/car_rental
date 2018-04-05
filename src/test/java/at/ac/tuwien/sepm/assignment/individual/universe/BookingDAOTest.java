package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.PaymentType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.*;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.BookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleBookingService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.SimpleVehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.service.VehicleService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

public class BookingDAOTest {

    private BookingDAO bookingDAO = new SimpleBookingDAO();
    private VehicleDAO vehicleDAO = new SimpleVehicleDAO();

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    private Booking booking = null;


    @BeforeClass
    public static void beforeClass() {
        DBConnection.setTestmode(true);
    }

    @AfterClass
    public static void afterClass() {
        DBConnection.closeConnection();
    }

    public BookingDAOTest() {
        List<Vehicle> bookingVehicles = new ArrayList<>();
        try {
            bookingVehicles.add(vehicleDAO.getById((long) 3));
        } catch (PersistenceException e) {
            LOG.error(e.getMessage());
        }
        //String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status, LocalDateTime createtime
        booking = new Booking("Dr.Eich", PaymentType.CREDITCARD, "4532642579652817", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4), bookingVehicles, 2000, BookingStatus.BOOKED, LocalDateTime.now());
    }


    @Test
    public void cancelBookingValid() {
        try {
            bookingDAO.addBookingToDatabase(booking);
            try {
                bookingDAO.cancelBooking(booking);
            } catch (InvalidBookingException e) {
                fail();
            }
            Booking newBooking = bookingDAO.getBookingByID(booking.getId());
            Assert.assertEquals(BookingStatus.CANCELED, newBooking.getStatus());
        } catch (PersistenceException e) {
            fail();
        }

    }

    @Test(expected = InvalidBookingException.class)
    public void cancelBookingInvalid() throws PersistenceException, InvalidBookingException {

        bookingDAO.addBookingToDatabase(booking);
        bookingDAO.finishBooking(booking);
        booking.setStatus(BookingStatus.PAID);
        bookingDAO.cancelBooking(booking);

        Booking newBooking = null;
        newBooking = bookingDAO.getBookingByID(booking.getId());
        Assert.assertEquals(BookingStatus.CANCELED, newBooking.getStatus());
    }


    @Test(expected = IllegalArgumentException.class)
    public void createInvalidVehicle() throws PersistenceException {
        bookingDAO.addBookingToDatabase(null);
    }

}


