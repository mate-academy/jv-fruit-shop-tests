package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreator();
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    void createReport_nullInputData_NotOk() {
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(),
                "No data for the report!");
    }

    @Test
    void createReport_allValidData_OK() {
        Storage.FRUITS.put("apple", 200);
        Storage.FRUITS.put("banana", 150);
        Storage.FRUITS.put("lemon", 100);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,150" + System.lineSeparator()
                + "apple,200" + System.lineSeparator()
                + "lemon,100";
        assertEquals(expected, reportCreator.createReport());
    }
}
