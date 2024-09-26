package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceOperationTest {
    private HashMap<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
    }

    @Test
    void balancePositive_OK() {
        String fruit = "banana";
        int quantity = 100;
        Operation operation = Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.handle(storage, transaction);
        assertEquals(quantity, storage.get(fruit));
    }

    @Test
    void balanceZero_OK() {
        String fruit = "banana";
        int quantity = 0;
        Operation operation = Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.handle(storage, transaction);
        assertEquals(quantity, storage.get(fruit));
    }

    @Test
    void balanceNegative_NotOK() {
        String fruit = "banana";
        int quantity = -100;
        Operation operation = Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        assertThrows(RuntimeException.class, () -> balanceOperation.handle(storage, transaction));
    }
}
