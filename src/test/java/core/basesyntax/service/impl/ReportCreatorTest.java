package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_validData_Ok() {
        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("apple", 30);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyReport_ok() {
        String excpected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(excpected, actual);
    }
}
