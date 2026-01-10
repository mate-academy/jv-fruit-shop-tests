package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void shouldGenerateReportForMultipleFruits() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(
                Map.of("banana", 10, "apple", 5)
        );

        String expected =
                "fruit,quantity\n"
                        + "banana,10\n"
                        + "apple,5\n";

        assertEquals(expected, report);
    }

    @Test
    void shouldGenerateReportForEmptyMap() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();

        String report = generator.getReport(Map.of());

        assertEquals("fruit,quantity\n", report);
    }
}
