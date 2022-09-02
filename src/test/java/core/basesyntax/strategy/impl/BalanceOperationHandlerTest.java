package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void sumIsValid_Ok() {
        Storage.getStorage().put(new Fruit("banana"), 12);
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 21);
        balanceHandler.apply(transaction);
        Integer expected = 33;
        Integer actual =Storage.getStorage().get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void sumIsValid_NotOk() {
        balanceHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
