package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String RIGHT_REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,10";
    private static final String EMPTY_REPORT = "fruit,quantity";
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    void createReport_negativeBalance_notOk() {
        Storage.fruits.put("banana", -10);
        assertThrows(RuntimeException.class, () -> reportCreator.createReport(Storage.fruits));
    }

    @Test
    void createReport_rightData_ok() {
        Storage.fruits.put("banana", 10);
        assertEquals(RIGHT_REPORT, reportCreator.createReport(Storage.fruits).trim());
    }

    @Test
    void createReport_emptyStorage_ok() {
        assertEquals(EMPTY_REPORT, reportCreator.createReport(Storage.fruits).trim());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
