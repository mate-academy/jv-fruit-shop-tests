package core.basesyntax.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateServiceImpl implements DateService {

    @Override
    public String getCurrentDateString() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    @Override
    public String getNextDayDateString() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDayDate = currentDate.plusDays(1L);
        return nextDayDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

}
