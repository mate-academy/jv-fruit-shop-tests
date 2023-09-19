package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
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
    void report_ValidDataAfterProcessing_Ok() {
        Storage.getStorage().put("banana", 100);
        Storage.getStorage().put("apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator();
        assertEquals(expected, reportCreatorService.createReport());
    }

    @Test
    void report_NonValidDataAfterProcessing_NotOk() {
        Storage.getStorage().put("banana", 100);
        Storage.getStorage().put("apple", 100);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,100";
        assertNotEquals(expected, reportCreatorService.createReport());
    }

    @Test
    void report_EmptyData_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportCreatorService.createReport());
    }
}
