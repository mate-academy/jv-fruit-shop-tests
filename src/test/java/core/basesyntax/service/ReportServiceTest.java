package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;
    private static final String HEADER_ROW = "fruit,quantity" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.totalFruit.put("banana", 120);
        Storage.totalFruit.put("apple", 20);
    }

    @AfterEach
    void tearDown() {
        Storage.totalFruit.clear();
    }

    @Test
    void report_rightData_OK() {
        String expected = HEADER_ROW
                + "banana,120" + System.lineSeparator()
                + "apple,20" + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual,
                "Expected: \n" + expected + "\n actual: \n " + actual);

    }

    @Test
    void report_storageIsEmpty() {
        Storage.totalFruit.clear();
        String actual = reportService.createReport();
        assertEquals(HEADER_ROW, actual, " " + actual);
    }
}
