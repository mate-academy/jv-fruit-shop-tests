package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreator();
        Storage.fruitsMap.clear();
    }

    @Test
    void createReport_correctData_ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        Storage.fruitsMap.put("banana", 152);
        Storage.fruitsMap.put("apple", 90);
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
