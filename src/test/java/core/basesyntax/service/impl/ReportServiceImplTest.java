package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;
    private static Storage storage;

    @BeforeAll
    public static void setUp() {
        reportService = new ReportServiceImpl();
        storage = new Storage();

    }

    @Test
    public void reportService_createReportFromEmptyList_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(actualReport, expectedReport);
    }

    @Test
    public void reportService_createReport_ok() {
        storage.put("apple", 1);
        storage.put("banana", 2);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,2" + System.lineSeparator()
                + "apple,1" + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @AfterAll
    public static void afterAll() {
        Storage.fruitStorage.clear();
    }
}
