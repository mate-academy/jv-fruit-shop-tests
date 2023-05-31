package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void operationHandler_purchase_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 40);
        Transaction transaction = new Transaction(Operation.PURCHASE, banana, 21);
        operationHandler.apply(transaction);
        assertEquals(Integer.valueOf(19), Storage.storage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void operationHandler_purchase_notOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 40);
        Transaction transaction = new Transaction(Operation.PURCHASE, banana, 45);
        operationHandler.apply(transaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
