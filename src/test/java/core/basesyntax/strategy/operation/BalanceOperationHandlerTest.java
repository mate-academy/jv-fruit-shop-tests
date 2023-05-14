package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int QUANTITY = 20;
    private static FruitTransaction transaction;
    private static BalanceOperationHandler balanceOperation;

    @BeforeClass
    public static void beforeAll() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, QUANTITY);
        balanceOperation = new BalanceOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperation_handle_Ok() {
        balanceOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 20;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_notFoundInStorage_NotOk() {
        balanceOperation.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_withNullFruit_NotOk() {
        balanceOperation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_withEmptyFruit_NotOk() {
        balanceOperation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                EMPTY,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_withNullTransaction_NotOk() {
        balanceOperation.handle(new FruitTransaction(null, FRUIT, QUANTITY));
    }
}
