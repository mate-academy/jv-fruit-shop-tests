package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.impl.ReportCreatorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_emptyData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportCreatorService.createReport());
    }

    @Test
    void createReport_existingData_ok() {
        Storage.STORAGE.put("banana", 10);
        Storage.STORAGE.put("apple", 20);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator() + "apple,20" + System.lineSeparator();
        assertEquals(expectedReport, reportCreatorService.createReport());
    }

    @Test
    void createReport_OneItem_Ok() {
        Storage.STORAGE.put("banana", 10);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator();
        assertEquals(expectedReport,reportCreatorService.createReport());
    }
}
