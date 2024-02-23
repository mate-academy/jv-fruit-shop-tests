package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportService();
    }

    @Test
    void generateReport_storageEmpty_ok() {
        Assertions.assertTrue(reportService.generateReport().isBlank());
    }

    @Test
    void generateReport_storageFilled_ok() {
        Storage.getStorage().put("banana", 152);
        Storage.getStorage().put("apple", 90);
        String expected = "banana,152" + System.lineSeparator() + "apple,90";
        String actual = reportService.generateReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void generateReport_storageEmptyNotNull_ok() {
        Assertions.assertNotNull(reportService.generateReport());
    }

    @AfterEach
    void afterEachTest() {
        Storage.getStorage().clear();
    }
}
