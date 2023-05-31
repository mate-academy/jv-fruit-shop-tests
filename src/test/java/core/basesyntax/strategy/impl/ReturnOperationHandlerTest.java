package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void returnOperationHandler_equalsApply_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 10);
        Transaction returnOperation = new Transaction("r", banana, 10);
        operationHandler.apply(returnOperation);
        assertEquals(Integer.valueOf(20), Storage.storage.get(banana));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
