package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_negativeBalance_notOk() {
        Storage.fruits.put("banana", -10);
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(Storage.fruits));
    }

    @Test
    void createReport_rightData_ok() {
        String rightReport = "fruit,quantity"
                + System.lineSeparator() + "banana,10" + System.lineSeparator()
                + "apple,20";
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 20);
        assertEquals(rightReport, reportCreator.createReport(Storage.fruits));
    }

    @Test
    void createReport_emptyStorage_ok() {
        String emptyReport = "fruit,quantity";
        assertEquals(emptyReport, reportCreator.createReport(Storage.fruits));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
