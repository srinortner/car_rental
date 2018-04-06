package at.ac.tuwien.sepm.assignment.individual.vehiclerental.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public interface StatisticsService {

    public HashMap<LocalDate, Integer> getDataForTurnover(LocalDateTime startDate, LocalDateTime endDate);

    public HashMap<String, Integer> getDataForWeekdayBookingNumber(LocalDateTime startDate, LocalDateTime endDate);


}
