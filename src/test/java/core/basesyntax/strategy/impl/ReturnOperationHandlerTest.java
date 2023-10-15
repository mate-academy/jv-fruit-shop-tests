package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private Storage storage = new Storage();
    private ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler(storage);
    private Fruit apple = new Fruit("apple");

    @Test
    public void handleReturnOperation_ok() {
        storage.setFruitQuantity(apple, 5);
        FruitTransaction transaction = new FruitTransaction(Operation
                .RETURN, apple, 3);
        returnOperationHandler.handleOperation(transaction);
        int quantity = storage.getFruitQuantity(apple);

        assertEquals(8, quantity);
    }

    @Test
    public void failedToHandleReturnOperation_invalidQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .RETURN, apple, -3);

        assertThrows(RuntimeException.class, () -> returnOperationHandler
                .handleOperation(transaction));
    }
}
