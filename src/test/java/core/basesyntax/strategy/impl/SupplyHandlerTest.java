package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class SupplyHandlerTest {

    private final OperationHandler operationHandler = new SupplyHandler();
    private FruitTransaction fruitTransaction;

    @Test
    public void handle_CorrectDate_Ok() {
        Storage.getStorageMap().put("banana", 100);
        fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        operationHandler.handle(fruitTransaction);
        int expected = 110;
        int actual = Storage.getStorageMap().get("banana");
        Assert.assertEquals(expected, actual);
    }
}
