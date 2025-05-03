package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler operationHandler = new ReturnOperationHandler();

    @Before
    public void setUp() {
        OperationHandler balanceHandler = new BalanceOperationHandler();
        balanceHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "banana", 20));
        balanceHandler.handle(new FruitTransaction(StoreOperation.BALANCE, "apple", 30));
    }

    @Test
    public void handle_Ok() {
        operationHandler.handle(new FruitTransaction(StoreOperation.RETURN, "apple", 20));
        Integer actualResult = FruitStorage.storage.get("apple");
        Integer expectedResult = 50;
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void handle_NegativeAmount_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.RETURN, "banana", -10));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
