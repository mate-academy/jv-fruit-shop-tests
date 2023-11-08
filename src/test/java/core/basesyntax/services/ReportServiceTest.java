package core.basesyntax.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static ReportService reportService;
    private Map<String, Integer> storage;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storage = new LinkedHashMap<>();
    }

    @Test
    void testGenerate_reportWithValidData_OK() {
        storage.put("apple", 10);
        storage.put("banana", 5);

        String expected = FIRST_LINE + System.lineSeparator() + "apple,10"
                + System.lineSeparator() + "banana,5";
        String actual = reportService.generateReport(storage);

        assertEquals(expected, actual);
    }

    @Test
    void testGenerate_reportWithNullKey_NotOK() {
        storage.put(null, 10);

        assertThrows(IllegalArgumentException.class, () ->
                reportService.generateReport(storage));
    }

    @Test
    void testGenerate_reportWithNullValue_NotOK() {
        storage.put("apple", null);

        assertThrows(IllegalArgumentException.class, () ->
                reportService.generateReport(storage));
    }

    @Test
    void testGenerate_reportWithNullKeyAndValue_NotOk() {
        storage.put(null, null);

        assertThrows(IllegalArgumentException.class, () ->
                reportService.generateReport(storage));
    }
}
