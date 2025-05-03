package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportServiceImpl reportService;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_createReportFromEmptyList_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void reportService_createReport_ok() {
        storage = new Storage();
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
