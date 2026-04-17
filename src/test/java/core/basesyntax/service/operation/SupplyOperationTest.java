package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        storage = new HashMap<>();
    }

    @Test
    void handleOperation_nullStorage_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,null));
    }

    @Test
    void handleOperation_nullFruitTransaction_notOK() {
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(null,storage));
    }

    @Test
    void handleOperation_nullOperation_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                null, "banana", 1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_emptyFruit_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "", 1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_nullFruit_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, null, 1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_invalidQuantity_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", -1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_invalidOperation_notOK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -1);
        assertThrows(RuntimeException.class,
                () -> supplyOperation.handleOperation(fruitTransaction,storage));
    }

    @Test
    void handleOperation_validInput_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 1);
        storage.put("banana", 10);
        supplyOperation.handleOperation(fruitTransaction,storage);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 11);
        assertEquals(expected, storage);
    }

}
