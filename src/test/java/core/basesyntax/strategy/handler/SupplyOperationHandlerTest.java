package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void operationHandler_supply_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 40);
        Transaction transaction = new Transaction(Operation.SUPPLY, banana, 21);
        operationHandler.apply(transaction);
        assertEquals((Integer) 61, Storage.storage.get(banana));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
