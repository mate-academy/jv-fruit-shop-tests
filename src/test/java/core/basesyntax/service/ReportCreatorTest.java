package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    private ReportCreator reportCreator;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        reportCreator = new ReportCreator();
        storage = new Storage();
    }

    @Test
    void createReport_SuccessfulCreation_Ok() {
        storage.setFruits("banana",30);
        storage.setFruits("apple",50);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,50";
        String actualReport = reportCreator.createReport(storage);
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_NullStorage_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            reportCreator.createReport(null);
        });
    }

    @Test
    void createReport_ZeroQuantityFruit_NotOk() {
        storage.setFruits("banana", 0);
        storage.setFruits("apple", 50);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50";
        String actualReport = reportCreator.createReport(storage);
        assertEquals(expectedReport, actualReport);
    }
}
