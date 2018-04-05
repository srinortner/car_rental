package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import java.time.LocalDateTime;
import java.util.HashMap;

public class SimpleStatisticsService implements StatisticsService {
    @Override
    public HashMap<LocalDateTime, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate) {
        HashMap<LocalDateTime, Integer> dailyData = new HashMap<>();
        return dailyData;
    }

    @Override
    public HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
