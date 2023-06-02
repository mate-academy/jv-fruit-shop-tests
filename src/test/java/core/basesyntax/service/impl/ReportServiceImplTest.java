package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static Map<String, Integer> testStorage;
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
        testStorage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
    }

    @Test
    void createReport_storageWithThreeElements_ok() {
        testStorage.put("apple", 599);
        testStorage.put("peach", 135);
        testStorage.put("grape", 1180);
        String expected = "apple,599"
                + System.lineSeparator()
                + "peach,135"
                + System.lineSeparator()
                + "grape,1180";
        String actual = reportService.createReport(testStorage);

        assertEquals(expected, actual);
    }

    @Test
    void createReport_storageWithFiveElements_ok() {
        testStorage.put("apple", 599);
        testStorage.put("peach", 135);
        testStorage.put("grape", 1180);
        testStorage.put("orange", 745);
        testStorage.put("banana", 950);
        String expected = "orange,745"
                + System.lineSeparator()
                + "banana,950"
                + System.lineSeparator()
                + "apple,599"
                + System.lineSeparator()
                + "peach,135"
                + System.lineSeparator()
                + "grape,1180";
        String actual = reportService.createReport(testStorage);

        assertEquals(expected, actual);
    }
}
