package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_validData_ok() {
        Storage.FRUIT_STORAGE.put("banana", 120);
        Storage.FRUIT_STORAGE.put("apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        String actual = reportService.createReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.createReport();
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUIT_STORAGE.clear();
    }
}
