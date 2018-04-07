package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import at.ac.tuwien.sepm.assignment.individual.entities.Booking;
import at.ac.tuwien.sepm.assignment.individual.entities.LicenseType;

import java.time.LocalDate;
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
    public HashMap<LocalDate, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate) {


        HashMap<LocalDate, Integer> dailyData = new HashMap<>();
        List<Booking> bookingsInTimeframe = bookingService.getBookingsInTimeInterval(startDate, endDate);

        for (Booking booking : bookingsInTimeframe) {
            //If whole booking is in the timeframe
            if(booking.getStartDate().isAfter(startDate) && booking.getEndDate().isBefore(endDate)){
                LocalDate start = booking.getStartDate().toLocalDate();
                int dayscount = 1;
                while(start.isBefore(booking.getEndDate().toLocalDate())){
                    dayscount++;
                    start = start.plusDays(1);
                }
                int dailyPrice = booking.getTotalPrice()/dayscount;
                start = booking.getStartDate().toLocalDate();
                while(start.isBefore(booking.getEndDate().toLocalDate())){
                    if(dailyData.containsKey(start)){
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start,dailyTurnover);
                    } else {
                        dailyData.put(start,dailyPrice);
                    }
                    start = start.plusDays(1);
                }
                //If the booking starts before the timeframe
            } if(booking.getStartDate().isBefore(startDate) && booking.getEndDate().isBefore(endDate)){
                LocalDate start = startDate.toLocalDate();
                int dayscount = 1;
                while(start.isBefore(booking.getEndDate().toLocalDate())){
                    dayscount++;
                    start = start.plusDays(1);
                }
                int dailyPrice = booking.getTotalPrice()/dayscount;
                start = startDate.toLocalDate();
                while (start.isBefore(booking.getEndDate().toLocalDate())){
                    if(dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start,dailyTurnover);
                    } else {
                        dailyData.put(start,dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
            //If booking ends after the timeframe
            if(booking.getStartDate().isAfter(startDate) && booking.getEndDate().isAfter(endDate)){
                LocalDate start = booking.getStartDate().toLocalDate();
                int dayscount = 1;
                while(start.isBefore(endDate.toLocalDate())){
                    dayscount++;
                    start = start.plusDays(1);
                }
                start = booking.getStartDate().toLocalDate();
                int dailyPrice = booking.getTotalPrice()/dayscount;
                while(start.isBefore(endDate.toLocalDate())){
                    if(dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start,dailyTurnover);
                    } else {
                        dailyData.put(start,dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
            //If booking starts before and ends after timeframe
            if(booking.getStartDate().isBefore(startDate) && booking.getEndDate().isAfter(endDate)){
                LocalDate start = startDate.toLocalDate();
                int dayscount = 1;
                while(start.isBefore(endDate.toLocalDate())){
                    dayscount++;
                    start = start.plusDays(1);
                }
                start = booking.getStartDate().toLocalDate();
                int dailyPrice = booking.getTotalPrice()/dayscount;
                while(start.isBefore(endDate.toLocalDate())){
                    if(dailyData.containsKey(start)) {
                        int dailyTurnover = dailyData.get(start);
                        dailyTurnover += dailyPrice;
                        dailyData.put(start,dailyTurnover);
                    } else {
                        dailyData.put(start,dailyPrice);
                    }
                    start = start.plusDays(1);
                }
            }
        }
        return dailyData;
    }

    @Override
    public HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
