package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportServiceImpl reportService;

    @BeforeAll
    static void init() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void generateReport_emptyStorage_ok() {
        String expected = "fruit, quantity";
        String actual = reportService.generateReport(Collections.emptyMap());
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_nonEmptyStorage_ok() {
        String expected = "fruit, quantity\r\nbanana,100\r\norange,50";
        Map<String, Integer> fruitMap = new LinkedHashMap<>();
        fruitMap.put("banana", 100);
        fruitMap.put("orange", 50);
        String actual = reportService.generateReport(fruitMap);
        assertEquals(expected, actual);
    }
}
