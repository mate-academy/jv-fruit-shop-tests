package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void setup() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_successful() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("apple", 10);
        testMap.put("banana", 10);
        List<String> testList = reportService.createReport(testMap);
        assertEquals("apple,10", testList.get(1));
        assertEquals("banana,10", testList.get(0));
    }

    @Test
    void createReport_emptyInput_throwsException() {
        Map<String, Integer> emptyMap = new HashMap<>();
        assertThrows(RuntimeException.class, () -> {
            reportService.createReport(emptyMap);
        });
    }
}
