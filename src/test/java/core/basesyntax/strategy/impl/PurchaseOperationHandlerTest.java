package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private Storage storage = new Storage();
    private PurchaseOperationHandler operationHandler = new PurchaseOperationHandler(storage);
    private Fruit apple = new Fruit("apple");

    @Test
    public void handlePurchaseOperation_ok() {
        storage.setFruitQuantity(apple, 15);
        FruitTransaction transaction = new FruitTransaction(Operation
                .PURCHASE, apple, 10);
        operationHandler.handleOperation(transaction);
        int quantity = storage.getFruitQuantity(apple);

        assertEquals(5, quantity);
    }

    @Test
    public void failedToHandlePurchaseOperation_notEnoughFruits() {
        storage.setFruitQuantity(apple, 5);
        FruitTransaction transaction = new FruitTransaction(Operation
                .PURCHASE, apple, 10);

        assertThrows(RuntimeException.class, () -> operationHandler
                .handleOperation(transaction));
    }

    @Test
    public void failedToHandlePurchaseOperation_invalidQuantity() {
        storage.setFruitQuantity(apple, 15);
        FruitTransaction transaction = new FruitTransaction(Operation
                .PURCHASE, apple, -10);

        assertThrows(RuntimeException.class, () -> operationHandler
                .handleOperation(transaction));
    }
}
