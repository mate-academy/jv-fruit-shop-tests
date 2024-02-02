package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Storage.storage.clear();
    }

    @Test
    public void testCreateReportWithEmptyStorage() {
        String report = reportService.createReport();
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    public void testCreateReportWithNonEmptyStorage() {
        Storage.storage.put("Apple", 10);
        Storage.storage.put("Banana", 5);
        String report = reportService.createReport();
        assertEquals("fruit,quantity\nApple,10\nBanana,5\n", report);
    }

    @Test
    public void testCreateReportWithZeroQuantity() {
        Storage.storage.put("Orange", 0);
        String report = reportService.createReport();
        assertEquals("fruit,quantity\nOrange,0\n", report);
    }
}
