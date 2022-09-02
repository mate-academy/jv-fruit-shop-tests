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
    public static void beforeClass() {
        returnHandler = new ReturnOperationHandler();
    }

    @Test
    public void returnIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction
                .Operation.RETURN, new Fruit("apple"), 33);
        Storage.getStorage().put(new Fruit("apple"), 30);
        returnHandler.apply(transaction);
        Integer expected = 63;
        Integer actual = Storage.getStorage().get(new Fruit("apple"));
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void returnIsValid_NotOk() {
        returnHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
