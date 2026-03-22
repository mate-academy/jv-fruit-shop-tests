package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        Storage.clear();

    }

    @Test
    void apply_shouldSetCorrectlyQuantityToStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        balanceOperation.apply(transaction);
        int actual = Storage.getQuantity("banana");
        assertEquals(50, actual);
    }
}