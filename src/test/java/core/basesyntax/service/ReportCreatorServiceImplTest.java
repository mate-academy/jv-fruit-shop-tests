package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void createReport_emptyData_ok() {
        String expected = "fruit,quantity";
        assertEquals(expected, reportCreatorService.createReport());
    }

    @Test
    void createReport_existingData_ok() {
        Storage.getStorage().put("banana", 10);
        Storage.getStorage().put("apple", 20);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + System.lineSeparator()
                + "banana,10" + System.lineSeparator() + "apple,20";
        assertEquals(expectedReport, reportCreatorService.createReport());
    }

    @Test
    void createReport_OneItem_Ok() {
        Storage.getStorage().put("banana", 10);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + System.lineSeparator()
                + "banana,10";
        assertEquals(expectedReport,reportCreatorService.createReport());
    }
}
