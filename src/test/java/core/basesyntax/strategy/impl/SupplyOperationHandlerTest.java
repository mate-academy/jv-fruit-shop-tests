package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private Storage storage = new Storage();
    private SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler(storage);
    private Fruit apple = new Fruit("apple");

    @Test
    public void handleSupplyOperation_ok() {
        storage.setFruitQuantity(apple, 5);
        FruitTransaction transaction = new FruitTransaction(Operation
                .SUPPLY, apple, 3);
        supplyOperationHandler.handleOperation(transaction);
        int quantity = storage.getFruitQuantity(apple);

        assertEquals(8, quantity);
    }

    @Test
    public void failedToHandleSupplyOperation_InvalidQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .SUPPLY, apple, -3);

        assertThrows(RuntimeException.class, () -> supplyOperationHandler
                .handleOperation(transaction));
    }
}
