package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplyHandler_Ok() {
        FruitStorage.fruits.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 40);
        operationHandler.handle(transaction);
        int excepted = 60;
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(excepted, actual);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruits.clear();
    }
}
