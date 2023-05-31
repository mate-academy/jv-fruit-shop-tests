package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class BalanceHandlerTest {
    private final OperationHandler operationHandler = new BalanceHandler();
    private FruitTransaction fruitTransaction;

    @Test
    public void handle_CorrectDate_Ok() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        operationHandler.handle(fruitTransaction);
        int expectedFruitQuantity = 100;
        int actualQuantity = Storage.getStorageMap().get("banana");
        Assert.assertEquals(expectedFruitQuantity, actualQuantity);
    }
}
