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
    private final BalanceOperation operation = new BalanceOperation();

    @Before
    public void before() {
        Storage.fruits.clear();
    }

    @Test
    public void handle_newValue_ok() {
        int expected = FRUIT_COUNT;
        operation.handle(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, FRUIT_COUNT));
        int actual = Storage.fruits.get(FRUIT_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_existValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_NAME, FRUIT_COUNT));
        operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_NAME, FRUIT_COUNT));
    }

    @Test
    public void handle_nullValue_notOk() {
        BalanceOperation operation = new BalanceOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    null, FRUIT_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true, "
                + "but false was expected", thrown);
    }
}
