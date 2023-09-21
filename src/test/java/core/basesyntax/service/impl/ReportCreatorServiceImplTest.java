package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_createdCorrectReport_Ok() {
        Storage.STORAGE.put("banana", 152);
        Storage.STORAGE.put("apple", 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        String actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
}
