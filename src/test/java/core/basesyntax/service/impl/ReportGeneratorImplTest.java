package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private ReportGenerator reportGenerator;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_ShouldReturnHeaderOnly_WhenStorageIsEmpty() {
        String expectedReport = "fruit,quantity\n";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport,
                "Report for empty storage should only contain the header.");
    }

    @Test
    void getReport_ShouldReturnSingleFruitEntry_WhenStorageHasOneFruit() {
        storage.updateFruitQuantity("apple", 50);

        String expectedReport = "fruit,quantity\napple,50\n";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport,
                "Report should contain one fruit entry with the correct quantity.");
    }

    @Test
    void getReport_ShouldHandleZeroQuantityForFruit() {
        storage.updateFruitQuantity("kiwi", 0);

        String expectedReport = "fruit,quantity\nkiwi,0\n";
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport,
                "Report should display zero quantity for fruits with no stock.");
    }

}
