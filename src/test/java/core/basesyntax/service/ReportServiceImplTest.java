package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;
    private static Map<String, Integer> validMap;
    private static Map<String, Integer> nullMap;
    private static String expectedReportEmptyMap;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
        validMap = Map.of("banana", 10,"apple", 5);
        nullMap = null;
        expectedReportEmptyMap = "fruit,quantity";
    }

    @Test
    void createReport_validMap_Ok() {
        String actualReport = reportService.createReport(validMap);
        Assertions.assertTrue(actualReport.contains("fruit,quantity"),
                "Report should contain header, fruit name and quantity from Map");
        Assertions.assertTrue(actualReport.contains("banana,10"),
                "Report should contain header, fruit name and quantity from Map");
        Assertions.assertTrue(actualReport.contains("apple,5"),
                "Report should contain header, fruit name and quantity from Map");
    }

    @Test
    void createReport_nullMap_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> reportService.createReport(nullMap),
                "Should throw exception when Map is null");
        assertTrue(runtimeException.getMessage().equals("Map can't be null"));
    }

    @Test
    void createReport_emptyMap_NotOk() {
        String actualReport = reportService.createReport(Map.of());
        Assertions.assertEquals(actualReport, expectedReportEmptyMap,
                "If Map is empty, report should contain only header");
    }
}
