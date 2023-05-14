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
        FruitStorage.fruits.put("orange", 100);
    }

    @Test
    public void supplyHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 50);
        operationHandler.handle(transaction);
        int excepted = 150;
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(excepted, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
