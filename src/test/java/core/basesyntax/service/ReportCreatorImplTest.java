package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        Storage.fruits.clear();
    }

    @Test
    void createReport_ValidData_Ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 50);

        String expectedReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,50";

        String actualReport = reportCreator.createReport();

        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_EmptyStorage_Ok() {
        String expectedReport = "fruit,quantity";

        String actualReport = reportCreator.createReport();

        assertEquals(expectedReport, actualReport);
    }
}
