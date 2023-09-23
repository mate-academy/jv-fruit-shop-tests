package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    public void purchaseHandler_NullTransactionFruit_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE,
                        null, 5);
        NullPointerException exception =
                assertThrows(NullPointerException.class, ()
                        -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Fruit cannot br null", exception.getMessage());
    }

    @Test
    public void purchaseHandler_NegativeQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE,
                        "apple", -5);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()
                        -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Transaction quantity cannot be negative", exception.getMessage());
    }

    @Test
    public void purchaseHandler_InsufficientStock_notOk() {
        Storage.storage.put("banana", 10);
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.PURCHASE,
                "banana", 15);
        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> purchaseHandler.handle(fruitTransaction));
        assertEquals("Not enough banana in stock. The value is 10"
                + " and you want to purchase 15", exception.getMessage());
    }
}
