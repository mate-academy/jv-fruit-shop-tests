package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.ReportServiceCsvImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceCsvImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.STORAGE.clear();
    }

    @Test
    void create_emptyReport_ok() {
        String expectedReport = "fruit,quantity";
        assertEquals(expectedReport, reportService.createReport());
    }

    @Test
    void create_reportAfterTransactions_ok() {
        FruitStorage.STORAGE.put("banana", 130);
        FruitStorage.STORAGE.put("orange", 40);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "orange,40";
        assertEquals(expectedReport, reportService.createReport());
    }
}
