package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_ValidData_Ok() {
        String expectedReport = "fruit,quantity\nbanana,15\napple,25";
        Storage.getStorage().put("apple", 25);
        Storage.getStorage().put("banana", 15);
        String report = reportCreator.prepare();
        assertEquals(expectedReport, report);
    }

    @Test
    public void testCreateReport_EmptyData() {
        String expectedReport = "fruit,quantity";
        String report = reportCreator.prepare();
        assertEquals(expectedReport, report);
    }

    @AfterEach
    void clearUp() {
        Storage.getStorage().clear();
    }
}
