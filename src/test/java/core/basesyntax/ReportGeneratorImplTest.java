package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReportGeneratorImpl;
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
}
