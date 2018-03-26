package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateBooking;

public class SimpleBookingService implements BookingService{

    private BookingDAO bookingDAO;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleBookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public Booking addBookingToPersistence(Booking booking) throws InvalidBookingException {
       validateBooking(booking);
       return bookingDAO.addBookingToDatabase(booking);
    }

    public void addLicenseInformationToPersistence (Long vehicleId, Long bookingId, String licensenumber, LocalDate licensedate) {
        bookingDAO.addLicenseToDatabase(vehicleId,bookingId,licensenumber,licensedate);
    }

    public List<Booking> getBookingsForVehicleFromPersistence(Long vehicleID){
        List<Booking> bookingList = bookingDAO.getAllBookingsOfVehicle(vehicleID);
        return bookingList;
    }

    public List<Booking> getAllBookingsFromPersistence(){
        return bookingDAO.getAllBookingsFromDatabase();
    }

    public void finishBookingInPersistence(Booking booking){
        bookingDAO.finishBooking(booking);
    }

    public void cancelBookingInPersistence(Booking booking){
        int cancellationFee = 0;
        int percentage = 0;
        if(LocalDateTime.now().plusDays(1).isAfter(booking.getStartDate())){
            percentage = 100;
            cancellationFee = booking.getTotalPrice();
        } else if (LocalDateTime.now().plusDays(3).isAfter(booking.getStartDate())) {
            percentage = 75;
            cancellationFee = (booking.getTotalPrice()/100) * percentage;
        } else if (LocalDateTime.now().plusDays(7).isAfter(booking.getStartDate())) {
            percentage = 40;
            cancellationFee = (booking.getTotalPrice()/100) * percentage;
        } else {
            cancellationFee = 0;
        }
        booking.setCancelingFeeInPercent(percentage);
        booking.setTotalPrice(cancellationFee);
        bookingDAO.cancelBooking(booking);
    }
}
