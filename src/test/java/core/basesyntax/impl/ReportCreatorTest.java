package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportPreparer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static ReportPreparer reportCreator;

    @BeforeAll
    static void setUp() {
        reportCreator = new ReportCreator();
    }

    @Test
    void createReport_ValidData_Ok() {
        Storage.getStorage().put("apple", 25);
        Storage.getStorage().put("banana", 15);
        String report = reportCreator.prepare();
        String expectedReport = "fruit,quantity\nbanana,15\napple,25";
        assertEquals(expectedReport, report);
    }

    @AfterEach
    void clearUp() {
        Storage.getStorage().clear();
    }
}
