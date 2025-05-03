package utilities;

import core.basesyntax.models.FruitTransaction;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    public static final String DATE_TIME_PATTERN = "dd.MM.yy HH-mm";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.ENGLISH);
    public static final String REPORT_TIME =
            " report for " + FORMATTER.format(LocalDateTime.now());
    public static final String FILE_PATH = "src/main/resources/";
    public static final String ORANGE = "orange";
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final String ANY_FRUIT = "anyFruit";
    public static final int INITIAL_QUANTITY_OF_ORANGE = 100;
    public static final int INITIAL_QUANTITY_OF_APPLE = 100;
    public static final int INITIAL_QUANTITY_OF_BANANA = 100;
    public static final FruitTransaction NO_SUCH_FRUIT_EXCEPTION_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, ANY_FRUIT, 10);
}
