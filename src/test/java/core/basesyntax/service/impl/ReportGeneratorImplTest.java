package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void shouldGenerateReportForMultipleFruits() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(
                Map.of("banana", 10, "apple", 5)
        );

        Assertions.assertTrue(report.startsWith("fruit,quantity\n"));
        Assertions.assertTrue(report.contains("banana,10\n"));
        Assertions.assertTrue(report.contains("apple,5\n"));
    }

    @Test
    void shouldGenerateReportForEmptyMap() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(Map.of());

        assertEquals("fruit,quantity\n", report);
    }
}
