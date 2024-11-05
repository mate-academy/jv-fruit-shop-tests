package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity\n";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_KIWI = "kiwi";
    private static final int QUANTITY_APPLE = 50;
    private static final int QUANTITY_KIWI_ZERO = 0;
    private static final String EXPECTED_REPORT_EMPTY_STORAGE = HEADER;
    private static final String EXPECTED_REPORT_SINGLE_FRUIT =
            HEADER + FRUIT_APPLE + "," + QUANTITY_APPLE + "\n";
    private static final String EXPECTED_REPORT_ZERO_QUANTITY =
            HEADER + FRUIT_KIWI + "," + QUANTITY_KIWI_ZERO + "\n";
    private ReportGenerator reportGenerator;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @Test
    void getReport_ShouldReturnHeaderOnly_WhenStorageIsEmpty() {
        String actualReport = reportGenerator.getReport();
        assertEquals(EXPECTED_REPORT_EMPTY_STORAGE, actualReport,
                "Report for empty storage should only contain the header.");
    }

    @Test
    void getReport_ShouldReturnSingleFruitEntry_WhenStorageHasOneFruit() {
        storage.updateFruitQuantity(FRUIT_APPLE, QUANTITY_APPLE);

        String actualReport = reportGenerator.getReport();
        assertEquals(EXPECTED_REPORT_SINGLE_FRUIT, actualReport,
                "Report should contain one fruit entry with the correct quantity.");
    }

    @Test
    void getReport_ShouldHandleZeroQuantityForFruit() {
        storage.updateFruitQuantity(FRUIT_KIWI, QUANTITY_KIWI_ZERO);

        String actualReport = reportGenerator.getReport();
        assertEquals(EXPECTED_REPORT_ZERO_QUANTITY, actualReport,
                "Report should display zero quantity for fruits with no stock.");
    }

}
