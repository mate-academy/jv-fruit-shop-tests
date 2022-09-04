package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void operationHandler_return_ok() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 40);
        Transaction transaction = new Transaction(Operation.RETURN, banana, 21);
        operationHandler.apply(transaction);
        assertEquals((Integer) 61, Storage.storage.get(banana));
    }

    @Test
    public void operationHandler_returnEmptyStorage_Ok() {
        Transaction transaction = new Transaction(Operation.RETURN, new Fruit("apple"), 21);
        operationHandler.apply(transaction);
        assertEquals(Integer.valueOf(21), Storage.storage.get(new Fruit("apple")));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
