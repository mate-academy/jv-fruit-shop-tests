package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void supplyOperationHandler_equalsApply_Ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 15);
        Transaction balanceOperation = new Transaction("b", banana, 30);
        operationHandler.apply(balanceOperation);
        assertEquals((Integer) 45, Storage.storage.get(banana));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
