package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruit = new Fruit("banana");
    }

    @Test
    public void balanceOperationIsValid_Ok() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                fruit, 55);
        Storage.getFruitsMap().put(fruit, 5);
        Integer expected = 60;
        balanceOperationHandler.apply(transaction);
        assertEquals(expected, Storage.getFruitsMap().get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperationIsInvalid_NotOk() {
        balanceOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.getFruitsMap().clear();
    }
}

