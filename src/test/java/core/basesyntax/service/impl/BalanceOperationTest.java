package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_validTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        balanceOperation.handle(transaction);
        int actualQuantity = Storage.getFruitQuantity("banana");
        assertEquals(100, actualQuantity);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -10);

        assertThrows(RuntimeException.class, () -> balanceOperation.handle(transaction));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
