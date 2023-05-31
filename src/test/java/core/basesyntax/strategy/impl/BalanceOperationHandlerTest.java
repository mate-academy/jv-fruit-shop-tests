package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationHandler_equalsApply_Ok() {
        Fruit banana = new Fruit("banana");
        Transaction balanceOperation = new Transaction("b", banana, 10);
        operationHandler.apply(balanceOperation);
        assertEquals(Integer.valueOf(10), Storage.storage.get(banana));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
