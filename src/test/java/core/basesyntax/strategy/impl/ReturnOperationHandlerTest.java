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
    }

    @Test
    public void returnHandler_Ok() {
        FruitStorage.fruits.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 40);
        operationHandler.handle(transaction);
        int excepted = 50;
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(excepted, actual);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruits.clear();
    }
}
