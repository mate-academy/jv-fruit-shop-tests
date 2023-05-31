package core.basesyntax.strategy.handlers.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler operationHandler = new BalanceOperationHandler();

    @Test
    public void balanceOperation_Ok() {
        String banana = "banana";
        Storage.storage.put(banana, 120);
        System.out.println(Storage.storage);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 100);
        operationHandler.handle(transaction);
        int expected = 100;
        int actual = Storage.storage.get(banana);
        System.out.println(Storage.storage);
        Assert.assertEquals(expected, actual);
    }
}
