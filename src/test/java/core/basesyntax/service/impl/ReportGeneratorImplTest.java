package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void getReport_validData_ok() {
        Map<String, Integer> data = Map.of("banana", 20, "apple", 50);
        String report = new ReportGeneratorImpl().getReport(data);
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("banana,20"));
        assertTrue(report.contains("apple,50"));
    }

    @Test
    void getReport_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> new ReportGeneratorImpl().getReport(null));
    }

    @Test
    void getReport_emptyData_ok() {
        String report = new ReportGeneratorImpl().getReport(new HashMap<>());
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }
}
