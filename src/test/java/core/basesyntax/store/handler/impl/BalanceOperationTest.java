package core.basesyntax.store.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void apply_shouldModifyStorageForBalanceOperation() {
        String fruit = "apple";
        int quantity = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        balanceOperation.apply(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result);
    }

    @Test
    void apply_shouldHandleZeroQuantity() {
        String fruit = "banana";
        int quantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        balanceOperation.apply(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result);
    }
}
