package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        Storage.storage.clear();
    }

    @Test
    void testCreateReport_ok() {
        Storage.storage.put("banana", 50);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void testCreateReport_emptyStorage() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
