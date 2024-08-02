package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @AfterEach
    void afterEach_setUp() {
        Storage.getFruitTransactions().clear();
    }

    @Test
    @DisplayName("Supply operation test")
    void calculateQuantitySupplyOperation_ok() {
        SupplyOperation supplyOperation = new SupplyOperation();
        supplyOperation.calculateQuantity(FruitTransaction.FruitName.APPLE, 100);
        int expectedStorageSizeAfterSupplyOperation = 1;
        int actualStorageSizeAfterSupplyOperation = Storage.getFruitTransactions().size();
        assertEquals(expectedStorageSizeAfterSupplyOperation,
                     actualStorageSizeAfterSupplyOperation);
    }

    @Test
    @DisplayName("Supply operation validate fruitName")
    void calculateQuantityFruitNameNull_notOk() {
        SupplyOperation supplyOperation = new SupplyOperation();
        assertThrows(RuntimeException.class, () -> supplyOperation.validateFruitName(null));
    }

    @Test
    @DisplayName("Supply operation validate fruitName")
    void calculateQuantityNegative_notOk() {
        SupplyOperation supplyOperation = new SupplyOperation();
        assertThrows(RuntimeException.class, () -> supplyOperation.validateQuantity(-10));
    }
}
