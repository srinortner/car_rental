package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.License;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.InvalidBookingException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.BookingDAO;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.persistence.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.AlertFactory.buildAlert;
import static at.ac.tuwien.sepm.assignment.individual.vehiclerental.util.Validator.validateBooking;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class SimpleBookingService implements BookingService {

    private BookingDAO bookingDAO;
    private VehicleService vehicleService;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    public SimpleBookingService(BookingDAO bookingDAO, VehicleService vehicleService) {
        this.bookingDAO = bookingDAO;
        this.vehicleService = vehicleService;
        vehicleService.setBookingService(this);
    }

    public Booking addBookingToPersistence(Booking booking) throws InvalidBookingException {
        validateBooking(booking);
        Booking returnedBooking = null;
        try {
            returnedBooking = bookingDAO.addBookingToDatabase(booking);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return returnedBooking;
    }

    public void addLicenseInformationToPersistence(Vehicle vehicle, Booking booking,  License license) {
        bookingDAO.addLicenseToDatabase(vehicle, booking, license);
    }

    public List<Booking> getAllBookingsFromPersistence() {
        return bookingDAO.getAllBookingsFromDatabase();
    }

    public void finishBookingInPersistence(Booking booking) {
        bookingDAO.finishBooking(booking);
    }

    public void cancelBookingInPersistence(Booking booking) throws ServiceException, InvalidBookingException{
        if(booking.getStatus() != BookingStatus.BOOKED){
            List<String> constraintViolations = new ArrayList<>();
            constraintViolations.add("Booking status is invalid!");
            throw new InvalidBookingException(constraintViolations);
        }
        int cancellationFee = 0;
        int percentage = 0;
        if (LocalDateTime.now().plusDays(1).isAfter(booking.getStartDate())) {
            percentage = 100;
            cancellationFee = booking.getTotalPrice();
        } else if (LocalDateTime.now().plusDays(3).isAfter(booking.getStartDate())) {
            percentage = 75;
            cancellationFee = (booking.getTotalPrice() / 100) * percentage;
        } else if (LocalDateTime.now().plusDays(7).isAfter(booking.getStartDate())) {
            percentage = 40;
            cancellationFee = (booking.getTotalPrice() / 100) * percentage;
        } else {
            cancellationFee = 0;
        }
        booking.setCancelingFeeInPercent(percentage);
        booking.setTotalPrice(cancellationFee);
        try {
            bookingDAO.cancelBooking(booking);
        } catch (PersistenceException e) {
            LOG.error("Booking couldn't be canceled", e);
           throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Long> getVehicleIDsFromPersistence(Booking booking) {
        return bookingDAO.getVehicleIDsFromDatabase(booking);
    }

    public boolean checkAvailiabilityOfVehicle(Vehicle vehicle, LocalDateTime currentStartTime, LocalDateTime currentEndTime) {
        boolean isAvailable = true;
        List<Booking> allBookingsOfVehicle;
        for (Vehicle legacyVehicle : vehicleService.getAllLegacyVehicles(vehicle)) {
            allBookingsOfVehicle = bookingDAO.getAllBookingsOfVehicle(legacyVehicle);
            for (Booking booking : allBookingsOfVehicle) {
                if (isAvailable && !(booking.getStatus() == BookingStatus.CANCELED)) {
                    if (booking.getPaidtime() == null) {
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
        }
        return isAvailable;
    }


    public void updateBookingInPersistence(Booking booking) {
        bookingDAO.updateBookingInDatabase(booking);
    }

    public Booking getBookingByIDFromPersistence (Long id) {
        try {
           return bookingDAO.getBookingByID(id);
        } catch (PersistenceException e) {
            LOG.error(e.getMessage(),e);
        }
        return null;
    }

    public void validateAddingVehicleToExistingBooking(Vehicle vehicle, Booking booking) throws InvalidBookingException{
        List<String> violations = new ArrayList<>();
        List<Long> vehicleIDsOfBooking = getVehicleIDsFromPersistence(booking);
        List<Vehicle> vehiclesOfBookingList = new ArrayList<>();
        for (Long id : vehicleIDsOfBooking) {
            Vehicle currentVehicle = vehicleService.getVehiclesByIDFromPersistence(id);
            vehiclesOfBookingList.add(currentVehicle);
        }
        booking.setBookedVehicles(vehiclesOfBookingList);
        if(!checkAvailiabilityOfVehicle(vehicle,booking.getStartDate(),booking.getEndDate())){
            violations.add("This vehicle is not available in that timeframe! ");
        }
        if(booking.getBookedVehicles().contains(vehicle)){
            violations.add("This booking already contains the selected vehicle! ");
        }
        if(!booking.getStatus().equals(BookingStatus.BOOKED)){
            violations.add("You can't add a vehicle to a paid/canceled booking! ");
        }
        if(!violations.isEmpty()){
            throw new InvalidBookingException(violations);
        }
        vehiclesOfBookingList.add(vehicle);
        booking.setBookedVehicles(vehiclesOfBookingList);
        validateBooking(booking);
    }

    public void updateTotalPrice(Booking booking){
        List<Vehicle> vehicleList = booking.getBookedVehicles();

        int dailyPrice = 0;
        for (Vehicle vehicle : vehicleList) {
            dailyPrice += vehicle.getHourlyRateCents() * 24;
        }


        int pricePerMinute = (dailyPrice / 24) / 60;
        Integer currentTotalPrice = 0;
        LocalDateTime i = booking.getStartDate();

        while (!i.equals(booking.getEndDate())) {
            currentTotalPrice += pricePerMinute;
            i = i.plusMinutes(1);
        }

        booking.setTotalPrice(currentTotalPrice);
        bookingDAO.updateTotalPriceInDatabase(booking);

    }

    public List<Booking> getBookingsInTimeInterval(LocalDateTime startTime, LocalDateTime endTime){
       return bookingDAO.getBookingsInTimeIntervalFromDatabase(startTime,endTime);
    }
}
