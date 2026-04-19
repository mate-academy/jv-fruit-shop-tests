package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        storage = new HashMap<>();
    }

    @Test
    void handleOperation_nullStorage_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,null));
    }

    @Test
    void handleOperation_nullFruitTransaction_notOK() {
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(null,storage));
    }

    @Test
    void handleOperation_nullOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                null, "banana", 1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_emptyFruit_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "", 1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_nullFruit_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_invalidQuantity_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_invalidOperation_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -1);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_validInput_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 1);
        balanceOperation.handleOperation(fruitTransaction,storage);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 1);
        assertEquals(expected, storage);
    }
}
