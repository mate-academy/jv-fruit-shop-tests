package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void generateReport_Ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
