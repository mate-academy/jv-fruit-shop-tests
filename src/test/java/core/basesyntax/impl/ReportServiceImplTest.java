package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @Test
    void createReport_emptyMap_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            reportService.createReport(null);
        });
    }

    @Test
    void createReport_validInput() {
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("apple", 10);
        testMap.put("banana", 20);
        List<String> testList = reportService.createReport(testMap);
        assertNotNull(testMap);
        assertEquals("apple,10", testList.get(1));
        assertEquals("banana,20", testList.get(0));
    }
}
