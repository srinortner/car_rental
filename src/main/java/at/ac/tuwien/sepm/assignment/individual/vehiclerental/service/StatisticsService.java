package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * service layer for statistics
 */
public interface StatisticsService {

    /**
     * gets all bookings in or partially in that time interval and calculates daily turnover
     * @param startDate of time interval
     * @param endDate of time interval
     * @return hashmap with key day and value of turnover of that day
     */
    HashMap<LocalDate, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * gets all bookings in or partially in that time interval and calculates number of bookings per day of week
     * @param startDate of time interval
     * @param endDate of time interval
     * @return hashmap with key day of week and value number of bookings on that weekday
     */
    HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate);


}
