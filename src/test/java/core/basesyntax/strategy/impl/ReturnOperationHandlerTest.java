package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new ReturnOperationHandler();
        FruitStorage.fruits.put("orange", 100);
    }

    @Test
    public void returnHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "orange", 5);
        operationHandler.handle(transaction);
        int excepted = 105;
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(excepted, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
