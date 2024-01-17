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
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_validMap_ok() {
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
    void createReport_nullMap_notOk() {
        assertThrows(RuntimeException.class, () -> reportService.createReport(null),
                "RuntimeException should be thrown if map is null!");
    }
}
