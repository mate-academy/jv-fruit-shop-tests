package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.reports.clear();
    }

    @Test
    void balance_nullFruitTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(null));
    }

    @Test
    void balance_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(
                new FruitTransaction(Operation.BALANCE, "apple", -1)));
    }

    @Test
    void balance_zeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 0);
        Map.Entry<String, Integer> expectedEntry = balanceOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void balance_maxIntQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE, "apple", Integer.MAX_VALUE);
        Map.Entry<String, Integer> expectedEntry = balanceOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void balance_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(
                new FruitTransaction(null, "apple", 10)));
    }

    @Test
    void balance_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(
                new FruitTransaction(Operation.BALANCE, null, 10)));
    }

    @Test
    void balance_incorrectOperation_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(
                new FruitTransaction(Operation.SUPPLY, "orange", 10)));
    }

    @Test
    void balance_Ok() {
        Storage.reports.put("banana", 100);
        balanceOperation.apply(new FruitTransaction(Operation.BALANCE, "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 57);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }
}
