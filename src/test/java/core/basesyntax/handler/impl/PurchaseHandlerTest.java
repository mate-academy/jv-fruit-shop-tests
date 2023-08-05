package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler purchaseHandler;
    private static final int DEFAULT_AMOUNT_OF_FRUITS = 100;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", DEFAULT_AMOUNT_OF_FRUITS);
        Storage.STORAGE.put("apple", DEFAULT_AMOUNT_OF_FRUITS);
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        fruitTransaction = new FruitTransaction();
        Storage.STORAGE.clear();
    }

    @Test
    void handleOperation_correctPurchaseOfBananas_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(10);
        fruitTransaction.setFruit("banana");
        purchaseHandler.handleOperation(fruitTransaction);
        int expected = 90;
        assertEquals(expected, Storage.STORAGE.get("banana"));
    }

    @Test
    void handleOperation_correctPurchaseOfApples_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(90);
        fruitTransaction.setFruit("apple");
        purchaseHandler.handleOperation(fruitTransaction);
        int expected = 10;
        assertEquals(expected, Storage.STORAGE.get("apple"));
    }

    @Test
    void handleOperation_purchaseTooMuchFruits_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(110);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.handleOperation(fruitTransaction));
    }

    @Test
    void handlerOperation_purchaseZeroItemsOfFruit_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(0);
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.handleOperation(fruitTransaction));
    }

    @Test
    void handleOperation_purchaseFruitWithNullType_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(10);
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.handleOperation(fruitTransaction));
    }
}
