package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.apply(storage, transaction);
        assertEquals(quantity, storage.get(fruit));
    }

    @Test
    void balanceZero_OK() {
        String fruit = "banana";
        int quantity = 0;
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.apply(storage, transaction);
        assertEquals(quantity, storage.get(fruit));
    }

    @Test
    void balanceNegative_NotOK() {
        String fruit = "banana";
        int quantity = -100;
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        BalanceOperation balanceOperation = new BalanceOperation();
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(storage, transaction));
    }
}
