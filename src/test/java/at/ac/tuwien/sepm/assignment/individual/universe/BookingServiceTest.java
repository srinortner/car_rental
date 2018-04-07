package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.PaymentType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
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

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

public class BookingServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BookingDAO bookingDAO = new SimpleBookingDAO();
    private VehicleDAO vehicleDAO = new SimpleVehicleDAO();
    private VehicleService vehicleService = new SimpleVehicleService(vehicleDAO);
    private BookingService bookingService = new SimpleBookingService(bookingDAO, vehicleService);


    private Booking booking = null;

    public BookingServiceTest() throws ServiceException {
        List<Vehicle> bookingVehicles = new ArrayList<>();
        bookingVehicles.add(vehicleService.getVehiclesByIDFromPersistence((long) 3));
        //String name, PaymentType paymentType, String paymentNumber, LocalDateTime startDate, LocalDateTime endDate, List<Vehicle> bookedVehicles, Integer totalPrice, BookingStatus status, LocalDateTime createtime
        booking = new Booking("Dr.Eich", PaymentType.CREDITCARD, "4532642579652817", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4), bookingVehicles, 2000, BookingStatus.BOOKED, LocalDateTime.now());

    }

    @BeforeClass
    public static void beforeClass() {
        DBConnection.setTestmode(true);
    }

    @AfterClass
    public static void afterClass() {
        DBConnection.closeConnection();
    }


    @Test
    public void cancelBookingValid() {
        try {
            bookingService.addBookingToPersistence(booking);
        } catch (InvalidBookingException e) {
            LOG.error("Booking was invalid");
        }
        try {
            bookingService.cancelBookingInPersistence(booking);
            Booking newBooking = bookingService.getBookingByIDFromPersistence(booking.getId());
            Assert.assertEquals(BookingStatus.CANCELED, newBooking.getStatus());
        } catch (ServiceException | InvalidBookingException e) {
            fail();
        }
    }

    @Test(expected = InvalidBookingException.class)
    public void cancelBookingInvalid() throws InvalidBookingException, ServiceException {
        booking.setStatus(BookingStatus.PAID);
        bookingService.addBookingToPersistence(booking);


        bookingService.cancelBookingInPersistence(booking);
        Booking newBooking = bookingService.getBookingByIDFromPersistence(booking.getId());
        Assert.assertEquals(BookingStatus.CANCELED, newBooking.getStatus());
    }


    @Test(expected = InvalidBookingException.class)
    public void createInvalidVehicle() throws ServiceException, IOException, InvalidBookingException {
        bookingService.addBookingToPersistence(null);
    }

}
