package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;
    private static Storage fruitStorage;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void createReportFromEmptyList_ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReportValidData_ok() {
        fruitStorage = new Storage();
        Storage.fruitsStorage.put("apple", 1);
        Storage.fruitsStorage.put("banana", 2);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,2" + System.lineSeparator()
                + "apple,1";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
