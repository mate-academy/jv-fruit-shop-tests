package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test void createReport_reportWithoutFigures_Ok() {
        String expected = "fruit,quantity";
        String report = reportCreator.createReport();
        assertEquals(expected, report);
    }

    @Test
    void createReport_reportWithFigures_Ok() {
        Storage.storage.put("banana", 100);
        Storage.storage.put("apple", 100);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        String report = reportCreator.createReport();
        assertEquals(expected, report);
    }

    @AfterEach
    void storageClear() {
        Storage.storage.clear();
    }
}
