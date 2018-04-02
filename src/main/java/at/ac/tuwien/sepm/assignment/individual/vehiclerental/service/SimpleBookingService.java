package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
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
       Booking returnedBooking = null;
        try {
            returnedBooking = bookingDAO.addBookingToDatabase(booking);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return  returnedBooking;
    }

    public void addLicenseInformationToPersistence (Long vehicleId, Long bookingId, String licensetype, String licensenumber, LocalDate licensedate) {
        bookingDAO.addLicenseToDatabase(vehicleId,bookingId,licensetype,licensenumber,licensedate);
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

    public List<Long> getVehicleIDsFromPersistence(Booking booking) {
       return bookingDAO.getVehicleIDsFromDatabase(booking);
    }

    public boolean checkAvailiabilityOfVehicle (Long id, LocalDateTime currentStartTime, LocalDateTime currentEndTime) {
        boolean isAvailable = true;
        List<Booking> allBookingsOfVehicle;
        allBookingsOfVehicle = bookingDAO.getAllBookingsOfVehicle(id);
        for (Booking booking: allBookingsOfVehicle) {
            if(isAvailable && !(booking.getStatus() == BookingStatus.CANCELED)) {
                if(booking.getPaidtime() == null) {
                    if (booking.getStartDate().isBefore(currentStartTime) && booking.getEndDate().isBefore(currentEndTime)) {
                        isAvailable = true;
                    } else if (booking.getStartDate().isAfter(currentStartTime) && booking.getEndDate().isAfter(currentEndTime)) {
                        isAvailable = true;
                    } else {
                        isAvailable = false;
                    }
                } else {
                    if (booking.getStartDate().isBefore(currentStartTime) && booking.getPaidtime().toLocalDateTime().isBefore(currentEndTime)) {
                        isAvailable = true;
                    } else if (booking.getStartDate().isAfter(currentStartTime) && booking.getPaidtime().toLocalDateTime().isAfter(currentEndTime)) {
                        isAvailable = true;
                    } else {
                        isAvailable = false;
                    }
                }
            }
        }
        return isAvailable;
    }
}
