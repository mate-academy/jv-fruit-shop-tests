package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportGeneratorService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private final ReportGeneratorService reportGeneratorService = new ReportGeneratorServiceImpl();

    @Test
    void generateReport_validData_success() {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("apple", 10);
        inventory.put("banana", 20);
        String report = reportGeneratorService.generateReport(inventory);
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,20"));
    }
}
