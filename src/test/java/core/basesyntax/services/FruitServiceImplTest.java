package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.UtilityReader;
import utilities.UtilityReaderImpl;

public class FruitServiceImplTest {
    private static final String DATE_TIME_PATTERN = "dd.MM.yy HH-mm";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.ENGLISH);
    private static final String REPORT_TIME =
            " report for " + FORMATTER.format(LocalDateTime.now());
    private static final String FILE_PATH = "src/main/resources/";
    private static final String FIRST_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSVSmallQuantities.csv";
    private static final String FIRST_PATH_RAW_REPORT_NAME = "SampleSourceCSVSmallQuantities";
    private static final String SECOND_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSV2BiggerQuantities.csv";
    private static final String SECOND_PATH_RAW_REPORT_NAME = "SampleSourceCSV2BiggerQuantities";
    private static final String THIRD_PATH_RAW_REPORT =
            "src/main/resources/SampleSourceCSV3ExtremelyBigQuantities.csv";
    private static final String THIRD_PATH_RAW_REPORT_NAME =
            "SampleSourceCSV3ExtremelyBigQuantities";
    private static final String FIRST_CURRENT_REPORT_NAME =
            FILE_PATH + FIRST_PATH_RAW_REPORT_NAME + REPORT_TIME;
    private static final String SECOND_CURRENT_REPORT_NAME =
            FILE_PATH + SECOND_PATH_RAW_REPORT_NAME + REPORT_TIME;
    private static final String THIRD_CURRENT_REPORT_NAME =
            FILE_PATH + THIRD_PATH_RAW_REPORT_NAME + REPORT_TIME;
    private static final String SMALL_QUANTITIES_EXPECTED_RESULT = """
            fruit,quantity\r
            banana,150\r
            apple,262""";
    private static final String BIGGER_QUANTITIES_EXPECTED_RESULT = """
            fruit,quantity\r
            banana,30000\r
            apple,52500""";
    private static final String EXTREMELY_BIG_QUANTITIES_EXPECTED_RESULT = """
            fruit,quantity\r
            banana,2080000000\r
            apple,2042500000""";

    private static UtilityReader utilityReader;

    @BeforeAll
    static void initReader() {
        utilityReader = new UtilityReaderImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    void processReport_thenRetrieveIt_Ok1() {
        FruitService.initVars(FIRST_PATH_RAW_REPORT);
        FruitService.processReport(FIRST_PATH_RAW_REPORT_NAME);
        assertEquals(SMALL_QUANTITIES_EXPECTED_RESULT,
                utilityReader.getDataFromList(utilityReader.readFile(FIRST_CURRENT_REPORT_NAME)));
    }

    @Test
    void processReport_thenRetrieveIt_Ok2() {
        FruitService.initVars(SECOND_PATH_RAW_REPORT);
        FruitService.processReport(SECOND_PATH_RAW_REPORT_NAME);
        assertEquals(BIGGER_QUANTITIES_EXPECTED_RESULT,
                utilityReader.getDataFromList(utilityReader.readFile(SECOND_CURRENT_REPORT_NAME)));
    }

    @Test
    void processReport_thenRetrieveIt_Ok3() {
        FruitService.initVars(THIRD_PATH_RAW_REPORT);
        FruitService.processReport(THIRD_PATH_RAW_REPORT_NAME);
        assertEquals(EXTREMELY_BIG_QUANTITIES_EXPECTED_RESULT,
                utilityReader.getDataFromList(utilityReader.readFile(THIRD_CURRENT_REPORT_NAME)));
    }

}
