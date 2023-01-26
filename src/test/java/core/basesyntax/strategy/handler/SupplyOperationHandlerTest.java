package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import org.junit.After;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler operationHandler = new SupplyOperationHandler();

    @Test
    public void handleSupplyOperation_Ok() {
        operationHandler.handle(new FruitTransaction(StoreOperation.SUPPLY, "apple", 50));
        Integer actualResult = FruitStorage.storage.get("apple");
        Integer expectedResult = 50;
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void handleNegativeSupplyOperation_NotOk() {
        operationHandler.handle(new FruitTransaction(StoreOperation.SUPPLY, "banana", -30));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
