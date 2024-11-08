package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final Map<String, Integer> DATA_FROM_MAP = new HashMap<>();
    private static final String DATA_FROM_REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,20"
            + System.lineSeparator()
            + "apple,15";
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        DATA_FROM_MAP.put("banana", 20);
        DATA_FROM_MAP.put("apple", 15);
    }

    @Test
    void getReport_isOK() {
        String actual = reportService.getReport(DATA_FROM_MAP);
        assertEquals(DATA_FROM_REPORT, actual);
    }

    @Test
    void getReport_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class, () -> reportService.getReport(null));
    }
}
