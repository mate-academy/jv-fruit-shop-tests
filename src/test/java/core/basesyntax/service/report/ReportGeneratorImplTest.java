package core.basesyntax.service.report;

import org.junit.jupiter.api.Assertions;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final String REPORT_TITLE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 30;
    private static final int SINGLE_FRUIT_QUANTITY = 100;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void getReport_validInput_ok() {
        Map<String, Integer> fruits = new TreeMap<>();
        fruits.put(APPLE, APPLE_QUANTITY);
        fruits.put(BANANA, BANANA_QUANTITY);

        String expectedReport = REPORT_TITLE + LINE_SEPARATOR
                + APPLE + "," + APPLE_QUANTITY + LINE_SEPARATOR
                + BANANA + "," + BANANA_QUANTITY + LINE_SEPARATOR;

        String actualReport = reportGenerator.getReport(fruits);

        Assertions.assertEquals(expectedReport, actualReport,
                "The generated report does not match the expected report. "
                        + "Check the report generation logic.");
    }

    @Test
    public void getReport_emptyInput_ok() {
        Map<String, Integer> emptyFruits = Collections.emptyMap();

        String expectedReport = REPORT_TITLE + LINE_SEPARATOR;
        String actualReport = reportGenerator.getReport(emptyFruits);

        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_singleEntry_ok() {
        Map<String, Integer> singleFruit = Map.of(APPLE, SINGLE_FRUIT_QUANTITY);

        String expectedReport = REPORT_TITLE + LINE_SEPARATOR
                + APPLE + "," + SINGLE_FRUIT_QUANTITY + LINE_SEPARATOR;
        String actualReport = reportGenerator.getReport(singleFruit);

        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void getReport_nullInput_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                        reportGenerator.getReport(null));
    }
}
