package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_validData_Ok() {
        Storage.storage.put("banana",25);
        Storage.storage.put("apple",5);
        String expectedReportString = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,5" + System.lineSeparator();
        assertEquals(expectedReportString,reportService.reportStorage());
    }

    @Test
    public void createReport_emptyData_Ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(), reportService.reportStorage());
    }

    @AfterEach
    public void afterEach() {
        Storage.storage.clear();
    }
}
