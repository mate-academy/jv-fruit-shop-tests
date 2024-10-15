package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperationHandler balanceOperation;
    private FruitRecord fruitRecord;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperationHandler();
        Storage.storage.clear();
    }

    @Test
    void testApply_shouldAddFruitToStorage_success() {
        String fruitName = "Apple";
        int quantity = 10;
        fruitRecord = new FruitRecord(FruitRecord.Operation.BALANCE, fruitName, quantity);

        balanceOperation.apply(fruitRecord);

        assertEquals(quantity, Storage.storage.get(fruitName));
    }

    @Test
    void testApply_negativeBalance_success() {
        String fruitName = "Banana";
        int quantity = -100;
        fruitRecord = new FruitRecord(FruitRecord.Operation.BALANCE, fruitName, quantity);
        balanceOperation.apply(fruitRecord);

        assertEquals(quantity, Storage.storage.get(fruitName));
    }

    @Test
    void testApply_NullOfBalance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            balanceOperation.apply(null);
        });
        assertEquals("Transaction cannot be null", exception.getMessage());
    }
}
