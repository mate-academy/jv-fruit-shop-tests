package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;
    private static final String expectedReport =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,300" + System.lineSeparator()
                    + "apple,125" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
        Storage.storage.clear();
        Storage.storage.put("banana", 300);
        Storage.storage.put("apple", 125);
    }

    @Test
    void createReport_correctDataOutput_Ok() {
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
