package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static String expectedReport;
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
        Storage.STORAGE.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_validInfo_ok() {
        Storage.STORAGE.put("banana", 1);
        Storage.STORAGE.put("apple", 22);
        Storage.STORAGE.put("pineapple", 333);
        expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,1" + System.lineSeparator()
                + "apple,22" + System.lineSeparator()
                + "pineapple,333";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_emptyStorage_ok() {
        expectedReport = "fruit,quantity";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
