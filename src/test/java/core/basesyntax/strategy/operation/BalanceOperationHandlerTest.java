package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Utils;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static BalanceOperationHandler balanceOperation;

    @BeforeClass
    public static void beforeAll() throws Exception {
        Storage.fruits.clear();
        transaction = Utils.createTransaction(FruitTransaction.Operation.BALANCE, FRUIT, EXPECTED);
        balanceOperation = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperation_handle_Ok() {
        balanceOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_notFoundInStorage_NotOk() {
        balanceOperation.handle(null);
    }

    @Test
    public void balanceOperation_withNullFruit_Ok() {
        balanceOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                                                        null,
                                                        EXPECTED));
    }

    @Test
    public void balanceOperation_withEmptyFruit_Ok() {
        balanceOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                EMPTY,
                EXPECTED));
    }

    @Test
    public void balanceOperation_withNullTransaction_Ok() {
        balanceOperation.handle(Utils.createTransaction(null,
                FRUIT,
                EXPECTED));
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
