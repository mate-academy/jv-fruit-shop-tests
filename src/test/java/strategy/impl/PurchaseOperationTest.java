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

class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.reports.clear();
    }

    @Test
    void purchase_nullFruitTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(null));
    }

    @Test
    void purchase_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(
                new FruitTransaction(Operation.PURCHASE, "apple", -1)));
    }

    @Test
    void purchase_zeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 0);
        Map.Entry<String, Integer> expectedEntry = purchaseOperation.apply(transaction);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }

    @Test
    void purchase_maxIntMoreThanHave_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(
                new FruitTransaction(Operation.PURCHASE, "kiwi", Integer.MAX_VALUE)));
    }

    @Test
    void purchase_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(
                new FruitTransaction(null, "apple", 10)));
    }

    @Test
    void purchase_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(
                new FruitTransaction(Operation.PURCHASE, null, 10)));
    }

    @Test
    void purchase_incorrectOperation_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(
                new FruitTransaction(Operation.RETURN, "kiwi", 10)));
    }

    @Test
    void purchase_Ok() {
        Storage.reports.put("banana", 100);
        purchaseOperation.apply(new FruitTransaction(Operation.PURCHASE, "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 43);
        assertTrue(Storage.reports.entrySet().contains(expectedEntry));
    }
}
