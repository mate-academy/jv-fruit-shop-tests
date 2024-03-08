package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    private static ReportService reportService;

    @BeforeAll
    static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createValidReport_OK() {
        Map<String, Integer> fruitData = Map.of("banana", 10, "apple", 3);
        String header = "fruit,quantity";
        String bananaReport = "banana,10";
        String appleReport = "apple,3";
        String actual = reportService.createReport(fruitData);
        assertTrue(actual.contains(header)
                        && actual.contains(bananaReport) && actual.contains(appleReport),
                "Error creating report!");

        fruitData = new HashMap<>();
        actual = reportService.createReport(fruitData);
        assertEquals(header, actual, "Error creating report!");
    }

    @Test
    void createReportWithNegativeValues_NotOK() {
        Map<String, Integer> fruitDataWithNegative = Map.of("banana", 10, "apple", -3);

        assertThrows(IllegalArgumentException.class, () ->
                        reportService.createReport(fruitDataWithNegative),
                "IllegalArgumentException should be thrown if negative values!");
    }

    @Test
    void createNullReport_NotOK() {
        assertThrows(RuntimeException.class, () -> reportService.createReport(null),
                "RuntimeException should be thrown if report is null!");
    }
}
