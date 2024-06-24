package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    void supply_nullFruitTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(null));
    }

    @Test
    void supply_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(
                new FruitTransaction(Operation.SUPPLY, "apple", -1)));
    }

    @Test
    void supply_zeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 0);
        Map.Entry<String, Integer> expectedEntry = supplyOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void supply_maxIntQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.SUPPLY, "apple", Integer.MAX_VALUE);
        Map.Entry<String, Integer> expectedEntry = supplyOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void supply_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(
                new FruitTransaction(null, "apple", 10)));
    }

    @Test
    void supply_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(
                new FruitTransaction(Operation.SUPPLY, null, 10)));
    }

    @Test
    void supply_incorrectOperation_notOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(
                new FruitTransaction(Operation.RETURN, null, 10)));
    }

    @Test
    void supply_Ok() {
        Storage.reports.put("banana", 100);
        supplyOperation.apply(new FruitTransaction(Operation.SUPPLY, "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 157);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }
}
