package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    public void returnIs_Ok() {
        Transaction transaction = new Transaction(Transaction
                .Operation.RETURN, new Fruit("apple"), 33);
        Storage.getStorage().put(new Fruit("apple"), 30);
        Integer expected = 63;
        returnHandler.apply(transaction);
        assertEquals(expected, Storage.getStorage().get(new Fruit("apple")));
    }

    @Test(expected = RuntimeException.class)
    public void return_NotOk() {
        returnHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getStorage().clear();
    }
}
