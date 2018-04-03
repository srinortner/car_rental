package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.DBConnection;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.SimpleBookingDAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidVehicle() throws PersistenceException {
        bookingDAO.addBookingToDatabase(null);
    }

}


