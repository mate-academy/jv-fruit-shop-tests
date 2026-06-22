package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void apply_validData_ok() {
        BalanceOperationHandler handler = new BalanceOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        handler.apply(transaction);
        assertEquals(100, Storage.storage.get("banana"));
    }
}
