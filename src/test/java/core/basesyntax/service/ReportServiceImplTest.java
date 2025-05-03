package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
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
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();

        Storage.STORAGE.put("banana", 10);
        Storage.STORAGE.put("apple", 100);
        String actualReport = reportService.createReport(Storage.STORAGE);

        assertEquals(expectedReport, actualReport, "Reports should be equal.");
    }

    @Test
    void createReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportService.createReport(Storage.STORAGE);
        assertEquals(expectedReport, actualReport, "Reports should be equal.");
    }
}
