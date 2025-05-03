package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionGenerateReportServiceImplTest {
    private static final String FIRST_LINE_REPORT = "fruit,quantity";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String SIGN_SEPARATOR = ",";
    private static final int APPLE_COUNT = 5;
    private static final int BANANA_COUNT = 7;

    private static TransactionGenerateReportService generateReportService;

    private Map<String, Integer> createFruitsCountData() {
        Map<String, Integer> fruitsCount = new HashMap<>();
        fruitsCount.put(BANANA, BANANA_COUNT);
        fruitsCount.put(APPLE, APPLE_COUNT);
        return fruitsCount;
    }

    @Before
    public void setUp() {
        generateReportService = new TransactionGenerateReportServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void report_nullData_notOk() {
        String report = generateReportService.generateReport(null);
    }

    @Test
    public void report_incorrectData_notOk() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(APPLE)
                .append(SIGN_SEPARATOR)
                .append(BANANA_COUNT)
                .append(System.lineSeparator())
                .append(BANANA)
                .append(SIGN_SEPARATOR)
                .append(APPLE_COUNT)
                .append(System.lineSeparator());
        String report = generateReportService.generateReport(createFruitsCountData());
        assertNotEquals(stringBuilder.toString(), report);
    }

    @Test
    public void report_correctData_ok() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FIRST_LINE_REPORT)
                .append(System.lineSeparator())
                .append(BANANA)
                .append(SIGN_SEPARATOR)
                .append(BANANA_COUNT)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(SIGN_SEPARATOR)
                .append(APPLE_COUNT)
                .append(System.lineSeparator());
        String report = generateReportService.generateReport(createFruitsCountData());
        assertEquals(stringBuilder.toString(), report);
    }
}
