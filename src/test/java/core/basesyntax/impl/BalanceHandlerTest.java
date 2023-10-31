package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler balanceHandler;
    private Map<String, Integer> storage;

    @BeforeEach
    public void setUp() {
        balanceHandler = new BalanceHandler();
        storage = new HashMap<>();
    }

    @Test
    public void testHandleTransaction_AddsToFruitInventory() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        balanceHandler.handleTransaction(transaction, storage);

        assertEquals(10, storage.get("apple"));
    }

    @Test
    public void testHandleTransaction_AddsMultipleToFruitInventory() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 5);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 7);

        balanceHandler.handleTransaction(transaction1, storage);
        balanceHandler.handleTransaction(transaction2, storage);

        assertEquals(5, storage.get("apple"));
        assertEquals(7, storage.get("banana"));
    }
}
