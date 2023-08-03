package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_validData_ok() {
        Storage.STORAGE.put("banana", 10);
        Storage.STORAGE.put("apple", 100);
        String actualReport = reportService.createReport(Storage.STORAGE);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        Assertions.assertEquals(expectedReport, actualReport, "Reports should be equal.");
    }

    @Test
    void createReport_emptyStorage_ok() {
        String actualReport = reportService.createReport(Storage.STORAGE);
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(expectedReport, actualReport, "Reports should be equal.");
    }
}
