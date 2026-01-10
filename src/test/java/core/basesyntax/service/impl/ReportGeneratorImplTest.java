package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void shouldGenerateReportForMultipleFruits() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(
                Map.of("banana", 10, "apple", 5)
        );

        assertTrue(report.startsWith("fruit,quantity\n"));
        assertTrue(report.contains("banana,10\n"));
        assertTrue(report.contains("apple,5\n"));
    }

    @Test
    void shouldGenerateReportForEmptyMap() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(Map.of());

        assertTrue(report.equals("fruit,quantity\n"));
    }
}
