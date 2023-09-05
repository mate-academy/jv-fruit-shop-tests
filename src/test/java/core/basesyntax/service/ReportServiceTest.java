package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_emptyStorage_Ok() {
        String expected = "";
        String actual = reportService.createReport(Collections.emptyMap());
        assertEquals(expected, actual);
    }

    @Test
    void createReport_nonEmptyStorage_Ok() {
        String expected = "banana,100" + System.lineSeparator()
                + "apple,24";
        Map<String, Integer> storage = new LinkedHashMap<>();
        storage.put("banana", 100);
        storage.put("apple", 24);
        String actual = reportService.createReport(storage);
        assertEquals(expected, actual);
    }
}
