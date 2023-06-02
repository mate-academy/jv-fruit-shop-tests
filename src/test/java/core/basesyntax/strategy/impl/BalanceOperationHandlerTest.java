package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;
    private Storage storage;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        storage = new Storage();
    }

    @Test
    public void handle_addNewProduct_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        Map<String, Integer> expected = Map.of("banana", 10);
        balanceOperationHandler.handle(transaction);
        Map<String, Integer> actual = Storage.FRUIT_STORAGE;
        assertEquals(expected, actual);
    }

    @Test
    public void handle_zeroQuantity_exceptionThrown_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 0);
        assertThrows(RuntimeException.class, () -> balanceOperationHandler.handle(transaction));
    }

    @Test
    public void handle_negativeQuantity_exceptionThrown_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", -5);
        assertThrows(RuntimeException.class, () -> balanceOperationHandler.handle(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUIT_STORAGE.clear();
    }
}
