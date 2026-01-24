package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private static ReportGeneratorImpl reportGenerator;

    @BeforeAll
    static void init() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_ShouldContainAllFruits_WhenMapHasMultipleEntries() {
        String report = reportGenerator.getReport(
                Map.of("banana", 10, "apple", 5)
        );

        assertTrue(report.startsWith("fruit,quantity\n"));
        assertTrue(report.contains("banana,10\n"));
        assertTrue(report.contains("apple,5\n"));
    }

    @Test
    void getReport_ShouldReturnOnlyHeader_WhenMapIsEmpty() {
        String report = reportGenerator.getReport(Map.of());
        assertEquals("fruit,quantity\n", report);
    }
}
