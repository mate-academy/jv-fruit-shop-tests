package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.DATABASE.clear();
    }

    @Test
    void generateReport_emptyReport_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedReport, reportService.generateReport());
    }

    @Test
    void generateReport_reportAfterTransactions_ok() {
        Storage.DATABASE.put("banana", 100);
        Storage.DATABASE.put("apple", 30);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        assertEquals(expectedReport, reportService.generateReport());
    }
}
