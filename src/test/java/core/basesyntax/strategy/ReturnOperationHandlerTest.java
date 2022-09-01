package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void returnOperationsIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.RETURN,
                new Fruit("apple"), 10);
        Storage.getFruitsMap().put(new Fruit("apple"), 10);
        Integer expected = 20;
        returnOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(new Fruit("apple")));
    }

    @Test(expected = RuntimeException.class)
    public void returnOperationIsInvalid_NotOk() {
        returnOperationHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruitsMap().clear();
    }
}
