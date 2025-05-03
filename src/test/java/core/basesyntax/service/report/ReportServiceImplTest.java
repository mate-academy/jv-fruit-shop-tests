package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BANANA_QUANTITY = 30;
    private static final int APPLE_QUANTITY = 69;
    private static final int BANANA_QUANTITY2 = 96;
    private static final String COMMA = ",";
    private static final String FRUIT = "fruit";
    private static final String QUANTITY = "quantity";

    private ReportService reportService = new ReportServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void reportService_correctInput_isOk() {
        Storage.storage.put(BANANA, BANANA_QUANTITY);
        String expected = createSingleFruitReport(BANANA, BANANA_QUANTITY);
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void reportService_multipleItems_isOk() {
        Storage.storage.put(APPLE, APPLE_QUANTITY);
        Storage.storage.put(BANANA, BANANA_QUANTITY2);
        String expected = generateFruitSummary(APPLE, APPLE_QUANTITY,
                BANANA, BANANA_QUANTITY2);
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    private String createSingleFruitReport(String fruit, int quantity) {
        StringBuilder str = new StringBuilder();
        str.append(FRUIT)
                .append(COMMA)
                .append(QUANTITY)
                .append(NEW_LINE)
                .append(BANANA)
                .append(COMMA)
                .append(BANANA_QUANTITY)
                .append(NEW_LINE);
        return str.toString();
    }

    private String generateFruitSummary(String fruit1, int quantity1,
                                        String fruit2, int quantity2) {
        StringBuilder str = new StringBuilder();
        str.append(FRUIT)
                .append(COMMA)
                .append(QUANTITY)
                .append(NEW_LINE)
                .append(BANANA)
                .append(COMMA)
                .append(BANANA_QUANTITY2)
                .append(NEW_LINE);
        str.append(APPLE)
                .append(COMMA)
                .append(APPLE_QUANTITY)
                .append(NEW_LINE);
        return str.toString();
    }
}
