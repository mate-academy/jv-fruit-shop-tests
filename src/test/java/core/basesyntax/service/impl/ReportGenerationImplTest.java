package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGenerationImplTest {
    private static ReportGeneration reportGeneration;

    @BeforeAll
    public static void setUp() {
        reportGeneration = new ReportGenerationImpl();
    }

    @Test
    public void correctGenerationIfStorageIsEmpty_Ok() {
        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity";

        assertEquals(expected, actual);
    }

    @Test
    public void correctGenerationIfStorageIsNotEmpty_Ok() {
        Storage.storage.put("banana", 10);

        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10";

        assertEquals(expected, actual);
        Storage.storage.clear();
    }
}
