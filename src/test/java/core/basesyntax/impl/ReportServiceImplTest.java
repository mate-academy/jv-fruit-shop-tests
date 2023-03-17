package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final Map<String, Integer> EMPTY_MAP = new HashMap<>();
    private static final Map<String, Integer> VALID_MAP = new HashMap<>();
    private static final Map<String, Integer> NULL_MAP = null;
    private static final ReportService reportService = new ReportServiceImpl();
    private static final String EXPECTED_REPORT =
            "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,221";

    @BeforeAll
    static void beforeAll() {
        VALID_MAP.put("banana", 12);
        VALID_MAP.put("apple", 221);
    }

    @Test
    void storageValid_ok() {
        String actual = reportService.createReport(VALID_MAP);
        assertEquals(EXPECTED_REPORT, actual);
    }

    @Test
    void storageEmpty_notOk() {
        assertThrows(RuntimeException.class, () ->
                reportService.createReport(EMPTY_MAP));
    }

    @Test
    void storageNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                reportService.createReport(NULL_MAP));
    }
}
