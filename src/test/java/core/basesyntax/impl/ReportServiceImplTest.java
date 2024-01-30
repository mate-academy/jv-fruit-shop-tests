package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 100);
    }

    @Test
    void createReport_correctReportData_Ok() {
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana,20"
                + System.lineSeparator()
                + "apple,100";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
