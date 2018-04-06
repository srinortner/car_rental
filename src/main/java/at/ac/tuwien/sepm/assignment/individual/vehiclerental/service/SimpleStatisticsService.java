package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.Vehicle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class SimpleStatisticsService implements StatisticsService {

    private VehicleService vehicleService = null;
    private BookingService bookingService = null;

    public SimpleStatisticsService(VehicleService vehicleService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.bookingService = bookingService;
    }

    @Override
    public HashMap<LocalDateTime, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate) {
        HashMap<LocalDateTime, Integer> dailyData = new HashMap<>();
        List<Booking> bookingsInTimeframe = bookingService.getBookingsInTimeInterval(startDate,endDate);
        int x = 0;
        return dailyData;
    }

    @Override
    public HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
