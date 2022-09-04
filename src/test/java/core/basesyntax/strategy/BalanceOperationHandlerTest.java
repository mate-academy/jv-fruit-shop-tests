package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void init() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void applyValidTransaction_Ok() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 100);
        balanceOperationHandler.apply(transaction);
        int expected = 100;
        int actual = Storage.getStorage().get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void applyNullTransaction_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
