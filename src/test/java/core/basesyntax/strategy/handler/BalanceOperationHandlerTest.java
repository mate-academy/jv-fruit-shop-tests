package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler operationHandler = new BalanceOperationHandler();

    @Test
    public void handleBalanceOperation_Ok() {
        operationHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", 90));
        Integer actualResult = FruitStorage.storage.get("banana");
        Integer expectedResult = 90;
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void handleBalanceOperationTwice_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", 30));
        operationHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", 90));
    }

    @Test(expected = RuntimeException.class)
    public void handleNegativeAmountBalanceOperation_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", -50));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
