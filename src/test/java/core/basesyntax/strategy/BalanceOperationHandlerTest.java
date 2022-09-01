package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperationIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 55);
        Storage.getFruitsMap().put(new Fruit("banana"), 5);
        Integer expected = 60;
        balanceOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(new Fruit("banana")));
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperationIsInvalid_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.getFruitsMap().clear();
    }
}

