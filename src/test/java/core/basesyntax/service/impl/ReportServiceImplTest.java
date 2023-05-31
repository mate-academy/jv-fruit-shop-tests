package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_validData_ok() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 80);
        String expected = "fruit,quantity\nbanana,20\napple,80\n";
        String actual = reportService.getReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createReport_noTitle_notOk() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 80);
        String unexpected = "banana,20\napple,80\n";
        String actual = reportService.getReport();
        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void createReport_noSeparators_notOk() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 80);
        String unexpected = "fruit,quantitybanana,20apple,80";
        String actual = reportService.getReport();
        Assertions.assertNotEquals(unexpected, actual);
    }

    @Test
    void createReport_emptyData_notOk() {
        Storage.storage.clear();
        String expected = "fruit,quantity\n";
        String actual = reportService.getReport();
        Assertions.assertEquals(expected, actual);
    }
}
