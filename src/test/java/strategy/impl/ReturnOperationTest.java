package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import java.util.Map;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @Test
    void return_nullFruitTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.apply(null));
    }

    @Test
    void return_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.apply(
                new FruitTransaction(Operation.RETURN, "apple", -1)));
    }

    @Test
    void return_zeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 0);
        Map.Entry<String, Integer> expectedEntry = returnOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void return_maxIntQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.RETURN, "apple", Integer.MAX_VALUE);
        Map.Entry<String, Integer> expectedEntry = returnOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void return_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.apply(
                new FruitTransaction(null, "apple", 10)));
    }

    @Test
    void return_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.apply(
                new FruitTransaction(Operation.RETURN, null, 10)));
    }

    @Test
    void return_incorrectOperation_notOk() {
        assertThrows(RuntimeException.class, () -> returnOperation.apply(
                new FruitTransaction(Operation.SUPPLY, "kiwi", 10)));
    }

    @Test
    void return_Ok() {
        Storage.reports.put("banana", 100);
        returnOperation.apply(new FruitTransaction(Operation.RETURN, "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 157);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }
}
