package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.handlers.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private static final String FRUIT_NAME = "strawberry";
    private static final int FRUIT_COUNT = 20;

    @Before
    public void before() {
        Storage.fruits.clear();
    }

    @Test
    public void addNew_ok() {
        BalanceOperation balanceOperation = new BalanceOperation();
        int expected = FRUIT_COUNT;
        balanceOperation.handle(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, FRUIT_COUNT));
        int actual = Storage.fruits.get(FRUIT_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test
    public void addExisted_notOk() {
        BalanceOperation operation = new BalanceOperation();
        boolean thrown = false;
        operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_NAME, FRUIT_COUNT));
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    FRUIT_NAME, FRUIT_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }

    @Test
    public void addNull_notOk() {
        BalanceOperation operation = new BalanceOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    null, FRUIT_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }
}
