package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.BookingStatus;
import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehiclerental.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleStatisticsService implements StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VehicleService vehicleService = null;
    private BookingService bookingService = null;

    public SimpleStatisticsService(VehicleService vehicleService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.bookingService = bookingService;
    }

    @Override
    public HashMap<LocalDate, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate) {
        HashMap<LocalDate, Integer> dailyData = new HashMap<>();
        List<Booking> bookingsInTimeframe = null;
        try {
            bookingsInTimeframe = bookingService.getBookingsInTimeInterval(startDate, endDate);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

        for (Booking booking : bookingsInTimeframe) {
            LocalDate start = booking.getStartDate().toLocalDate();
            int dayscount = 1;
            while (start.isBefore(booking.getEndDate().toLocalDate())) {
                dayscount++;
                start = start.plusDays(1);
            }
            int dailyPrice = booking.getTotalPrice() / dayscount;
            //If whole booking is in the timeframe
            if (booking.getStartDate().isAfter(startDate) && booking.getEndDate().isBefore(endDate)) {
                start = booking.getStartDate().toLocalDate();
                while (start.isBefore(booking.getEndDate().toLocalDate())) {
                    if (dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start, dailyTurnover);
                    } else {
                        dailyData.put(start, dailyPrice);
                    }
                    start = start.plusDays(1);
                }
                //If the booking starts before the timeframe
            }
            if (booking.getStartDate().isBefore(startDate) && booking.getEndDate().isBefore(endDate)) {
                start = startDate.toLocalDate();
                while (start.isBefore(booking.getEndDate().toLocalDate())) {
                    if (dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start, dailyTurnover);
                    } else {
                        dailyData.put(start, dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
            //If booking ends after the timeframe
            if (booking.getStartDate().isAfter(startDate) && booking.getEndDate().isAfter(endDate)) {
                start = booking.getStartDate().toLocalDate();
                while (start.isBefore(endDate.toLocalDate())) {
                    if (dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start, dailyTurnover);
                    } else {
                        dailyData.put(start, dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
            //If booking starts before and ends after timeframe
            if (booking.getStartDate().isBefore(startDate) && booking.getEndDate().isAfter(endDate)) {
                start = booking.getStartDate().toLocalDate();
                while (start.isBefore(endDate.toLocalDate())) {
                    if (dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start, dailyTurnover);
                    } else {
                        dailyData.put(start, dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
        }
        return dailyData;
    }


    @Override
    public HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate, List<LicenseType> licenseTypes) {

        HashMap<String, Integer> dailyData = new HashMap<>();
        dailyData.put("MONDAY", 0);
        dailyData.put("TUESDAY", 0);
        dailyData.put("WEDNESDAY", 0);
        dailyData.put("THURSDAY", 0);
        dailyData.put("FRIDAY", 0);
        dailyData.put("SATURDAY", 0);
        dailyData.put("SUNDAY", 0);
        List<Booking> bookingsInTimeframe = new ArrayList<>();
        try {
            bookingsInTimeframe = bookingService.getBookingsInTimeInterval(startDate, endDate);
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

        //checks if all license requirements are met
        for (Booking booking : bookingsInTimeframe) {
            if(!booking.getStatus().equals(BookingStatus.CANCELED)) {
                try {
                    List<Long> currentIDs = bookingService.getVehicleIDsFromPersistence(booking);
                    List<Vehicle> vehiclesOfBooking = new ArrayList<>();
                    for (Long id : currentIDs) {
                        Vehicle current = vehicleService.getVehiclesByIDFromPersistence(id);
                        //if list is empty add all vehicles
                        if (licenseTypes.isEmpty()) {
                            vehiclesOfBooking.add(current);
                        } else {
                            //If NONE is Selected
                            if (licenseTypes.contains(LicenseType.NONE)) {
                                if (current.getLicenseType().isEmpty()) {
                                    vehiclesOfBooking.add(current);
                                }
                                for (int i = 0; i < licenseTypes.size() - 1; i++) {
                                    if (current.getLicenseType().contains(licenseTypes.get(i))) {
                                        if (!vehiclesOfBooking.contains(current)) {
                                            vehiclesOfBooking.add(current);
                                        }
                                    }
                                }
                            } else if (!licenseTypes.contains(LicenseType.NONE)) {
                                //if one of the license requirements is met
                                for (LicenseType license : licenseTypes) {
                                    if (current.getLicenseType().contains(license)) {
                                        if (!vehiclesOfBooking.contains(current)) {
                                            vehiclesOfBooking.add(current);
                                        }
                                    }
                                }
                            }
                        }

                    }
                    booking.setBookedVehicles(vehiclesOfBooking);
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

        for (Booking booking : bookingsInTimeframe) {
            if(!booking.getStatus().equals(BookingStatus.CANCELED)) {

                //If whole booking is in the timeframe
                if (booking.getStartDate().isAfter(startDate) && booking.getEndDate().isBefore(endDate)) {
                    LocalDate start = booking.getStartDate().toLocalDate();
                    while (start.isBefore(booking.getEndDate().toLocalDate())) {
                        String dayOfWeek = start.getDayOfWeek().toString();
                        int numberOfBookings = dailyData.get(dayOfWeek);
                        numberOfBookings += booking.getBookedVehicles().size();
                        dailyData.put(dayOfWeek, numberOfBookings);
                        start = start.plusDays(1);
                    }

                    //If the booking starts before the timeframe
                }
                if (booking.getStartDate().isBefore(startDate) && booking.getEndDate().isBefore(endDate)) {
                    LocalDate start = startDate.toLocalDate();
                    while (start.isBefore(booking.getEndDate().toLocalDate())) {
                        String dayOfWeek = start.getDayOfWeek().toString();
                        int numberOfBookings = dailyData.get(dayOfWeek);
                        numberOfBookings += booking.getBookedVehicles().size();
                        dailyData.put(dayOfWeek, numberOfBookings);
                        start = start.plusDays(1);
                    }
                }
                //If booking ends after the timeframe
                if (booking.getStartDate().isAfter(startDate) && booking.getEndDate().isAfter(endDate)) {
                    LocalDate start = booking.getStartDate().toLocalDate();
                    while (start.isBefore(endDate.toLocalDate())) {
                        String dayOfWeek = start.getDayOfWeek().toString();
                        int numberOfBookings = dailyData.get(dayOfWeek);
                        numberOfBookings += booking.getBookedVehicles().size();
                        dailyData.put(dayOfWeek, numberOfBookings);
                        start = start.plusDays(1);
                    }
                }
                //If booking starts before and ends after timeframe
                if (booking.getStartDate().isBefore(startDate) && booking.getEndDate().isAfter(endDate)) {
                    LocalDate start = startDate.toLocalDate();
                    while (start.isBefore(endDate.toLocalDate())) {
                        String dayOfWeek = start.getDayOfWeek().toString();
                        int numberOfBookings = dailyData.get(dayOfWeek);
                        numberOfBookings += booking.getBookedVehicles().size();
                        dailyData.put(dayOfWeek, numberOfBookings);
                        start = start.plusDays(1);
                    }
                }
            }

        }
        return dailyData;

    }
}
