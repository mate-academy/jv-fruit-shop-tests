package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImpTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImp();
    }

    @Test
    void createReportString_emptyStorage_ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportService.createReportString();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReportString_validStorage_ok() {
        Storage.fruitInventory.put("banana", 14);
        Storage.fruitInventory.put("apple", 88);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,14" + System.lineSeparator()
                + "apple,88";
        String actualReport = reportService.createReportString();
        assertEquals(expectedReport, actualReport);
    }

    @AfterAll
    public static void afterAll() {
        Storage.fruitInventory.clear();
    }
}
