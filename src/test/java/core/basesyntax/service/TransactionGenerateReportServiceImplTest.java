package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TransactionGenerateReportServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String SIGN_SEPARATOR = ",";
    private static final int APPLE_COUNT = 5;
    private static final int BANANA_COUNT = 7;

    private static final TransactionGenerateReportService generateReportService
            = new TransactionGenerateReportServiceImpl();

    private Map<String, Integer> createFruitsCountData() {
        Map<String, Integer> fruitsCount = new HashMap<>();
        fruitsCount.put(BANANA, BANANA_COUNT);
        fruitsCount.put(APPLE, APPLE_COUNT);
        return fruitsCount;
    }

    @Test
    void report_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            String report = generateReportService.generateReport(null);
        });
    }

    @Test
    void report_incorrectData_notOk() {
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
    void report_correctData_ok() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
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
