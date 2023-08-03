package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {

    private PurchaseHandler purchaseHandler;

    @BeforeEach
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void purchaseHandler_ValidTransaction_Ok() {
        Storage.storage.put("apple", 10);
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        purchaseHandler.handle(fruitTransaction);
        assertEquals(5, Storage.storage.get("apple"));
    }

    @Test
    public void purchaseHandler_InsufficientStock_NotOk() {
        Storage.storage.put("banana", 10);
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 15);
        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Not enough banana in stock. The value is 10"
                + " and you want to purchase 15", exception.getMessage());
    }

    @Test
    public void purchaseHandler_NullTransactionFruit_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                       null, 5);
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Fruit cannot br null", exception.getMessage());
    }

    @Test
    public void purchaseHandler_NegativeQuantity_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                       "apple", -5);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Transaction quantity cannot be negative", exception.getMessage());
    }
}
