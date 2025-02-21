package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGenerationImplTest {
    private static ReportGeneration reportGeneration;

    @BeforeAll
    public static void setUp() {
        reportGeneration = new ReportGenerationImpl();
    }

    @AfterEach
    public void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    public void reportGeneration_generationIfStorageIsEmpty_ok() {
        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity";

        assertEquals(expected, actual);
    }

    @Test
    public void reportGeneration_generationIfStorageIsNotEmpty_ok() {
        Storage.storage.put("banana", 10);

        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10";

        assertEquals(expected, actual);
    }

    @Test
    public void reportGeneration_generationWithSixtyMelons_ok() {
        Storage.storage.put("melon", 60);

        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "melon,60";

        assertEquals(expected, actual);
    }

    @Test
    public void reportGeneration_generationWithTwentyWatermelons_ok() {
        Storage.storage.put("watermelon", 20);

        String actual = reportGeneration.reportGeneration();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "watermelon,20";

        assertEquals(expected, actual);
    }
}
