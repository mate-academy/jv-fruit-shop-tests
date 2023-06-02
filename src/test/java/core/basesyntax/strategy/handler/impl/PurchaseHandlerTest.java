package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new PurchaseHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_quantityIsGreaterThanCurrent_notOK() {
        assertThrows(RuntimeException.class,()
                -> handler.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",
                11)));
    }

    @Test
    public void handle_quantityIsEqualToCurrent_ok() {
        Storage.FRUITS.put("apple", 10);
        Storage.FRUITS.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple",
                10);
        handler.handle(transaction);
        int updatedQuantity = Storage.FRUITS.get("apple");
        assertEquals(10 - transaction.getQuantity(), updatedQuantity);
    }

    @Test
    public void handle_quantityIsLessThanCurrent_ok() {
        Storage.FRUITS.put("apple", 10);
        Storage.FRUITS.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple",
                5);
        handler.handle(transaction);
        int updatedQuantity = Storage.FRUITS.get("apple");
        assertEquals(10 - 5, updatedQuantity);
    }

    @Test
    public void handle_negativeTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple",
                        -7)));
    }

    @Test
    public void handle_reduceFruitQuantityInStorage_ok() {
        Storage.FRUITS.put("apple", 10);
        Storage.FRUITS.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                20);
        handler.handle(transaction);
        Integer actualQuantity = Storage.FRUITS.get("banana");
        int expectedQuantity = 0;
        assertSame(expectedQuantity, actualQuantity);
    }
}
