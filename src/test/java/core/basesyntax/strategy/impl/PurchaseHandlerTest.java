package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    private final OperationHandler operationHandler = new PurchaseHandler();
    private FruitTransaction fruitTransaction;

    @Test
    public void handle_CorrectDate_Ok() {
        Storage.getStorageMap().put("banana", 100);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 10);
        operationHandler.handle(fruitTransaction);
        int expected = 90;
        int actual = Storage.getStorageMap().get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NotEnoughFruit_NotOk() {
        Storage.getStorageMap().put("banana", 100);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 200);
        operationHandler.handle(fruitTransaction);

    }
}
