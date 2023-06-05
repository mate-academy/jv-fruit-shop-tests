package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_storageWithFiveElements_ok() {
        Map<String, Integer> testStorage = new LinkedHashMap<>();
        testStorage.put("apple", 599);
        testStorage.put("peach",135);
        testStorage.put("grape", 1180);
        testStorage.put("orange", 745);
        testStorage.put("banana", 950);
        String expected = "apple,599"
                + System.lineSeparator()
                + "peach,135"
                + System.lineSeparator()
                + "grape,1180"
                + System.lineSeparator()
                + "orange,745"
                + System.lineSeparator()
                + "banana,950";
        String actual = reportService.createReport(testStorage);

        assertEquals(expected, actual);
    }
}
