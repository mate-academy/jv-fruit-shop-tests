package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
        fruit = new Fruit("apple");
    }

    @Test
    public void returnOperationsIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.RETURN,
                fruit, 10);
        Storage.getFruitsMap().put(fruit, 10);
        Integer expected = 20;
        returnOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void returnOperationIsInvalid_NotOk() {
        returnOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getFruitsMap().clear();
    }
}
