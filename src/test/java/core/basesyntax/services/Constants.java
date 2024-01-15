package core.basesyntax.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    public static final String ORANGE = "orange";
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final int INITIAL_QUANTITY_OF_ORANGE = 100;
    public static final int INITIAL_QUANTITY_OF_APPLE = 100;
    public static final int INITIAL_QUANTITY_OF_BANANA = 100;
    private static final String DATE_TIME_PATTERN = "dd.MM.yy HH-mm";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.ENGLISH);
    private static final String REPORT_NAME = "REPORT FOR " + FORMATTER.format(LocalDateTime.now());
    public static final String FILE_PATH = "src/main/resources/" + REPORT_NAME;
}
