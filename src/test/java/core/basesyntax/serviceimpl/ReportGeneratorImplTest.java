package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopInventory;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String TEST_FRUIT = "banana";
    private static final String EMPTY_FRUIT_VALUE = "";
    private static final int POSITIVE_FRUIT_QUANTITY_VALUE = 100;
    private static final int NEGATIVE_FRUIT_QUANTITY_VALUE = -100;

    @AfterEach
    void tearDown() {
        ShopInventory.inventory.clear();
    }

    @Test
    void getReport_ValidData_Ok() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        ShopInventory.inventory.put(TEST_FRUIT, POSITIVE_FRUIT_QUANTITY_VALUE);

        String finalReport = reportGenerator.getReport();

        assertFalse(finalReport.isEmpty());
    }

    @Test
    void getReport_EmptyFruitValue_NotOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        ShopInventory.inventory.put(EMPTY_FRUIT_VALUE, POSITIVE_FRUIT_QUANTITY_VALUE);

        assertThrows(RuntimeException.class, reportGenerator::getReport);
    }

    @Test
    void getReport_NegativeQuantity_NotOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        ShopInventory.inventory.put(TEST_FRUIT, NEGATIVE_FRUIT_QUANTITY_VALUE);

        assertThrows(RuntimeException.class, reportGenerator::getReport);
    }
}
