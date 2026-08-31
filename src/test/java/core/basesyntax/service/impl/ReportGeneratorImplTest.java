package core.basesyntax.service.impl;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    void getReport_validStorage_ok() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        String report = generator.getReport(Map.of("apple", 10, "banana", 5));

        Assertions.assertTrue(report.startsWith("fruit,quantity"));
        Assertions.assertTrue(report.contains("apple,10"));
    }

    @Test
    void getReport_emptyStorage_ok() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        String report = generator.getReport(Map.of());

        Assertions.assertEquals("fruit,quantity\n", report);
    }
}
