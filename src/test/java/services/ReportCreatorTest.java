package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.impl.ReportCreator;
import core.basesyntax.service.CreateReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorTest {
    private CreateReport createReport;

    @BeforeEach
    void setUp() {
        createReport = new ReportCreator();
        Storage.storage.clear();
    }

    @Test
    void createReport_ok() {
        Storage.storage.put("banana", 50);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator();
        String actual = createReport.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = createReport.createReport();
        assertEquals(expected, actual);
    }
}
