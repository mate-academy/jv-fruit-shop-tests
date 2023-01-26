package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler = new PurchaseOperationHandler();

    @Before
    public void setUp() {
        OperationHandler balanceHandler = new BalanceOperationHandler();
        balanceHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", 20));
        balanceHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "apple", 30));
    }

    @Test
    public void handlePurchaseOperation_Ok() {
        operationHandler.handle(new FruitTransaction(StoreOperation.PURCHASE, "apple", 20));
        Integer actualResult = FruitStorage.storage.get("apple");
        Integer expectedResult = 10;
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void handleNegativeAmountPurchaseOperation_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.PURCHASE, "apple", -10));
    }

    @Test(expected = RuntimeException.class)
    public void handleAmountPurchaseOperationLagerThanTotal_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.PURCHASE, "banana", 30));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
