package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Utils;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static ReturnOperationHandler returnOperation;

    @BeforeClass
    public static void beforeAll() throws Exception {
        Storage.fruits.clear();
        transaction = Utils.createTransaction(FruitTransaction.Operation.BALANCE, FRUIT, EXPECTED);
        returnOperation = new ReturnOperationHandler();
    }

    @Test
    public void returnOperation_handle_Ok() {
        returnOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_notFoundInStorage_NotOk() {
        returnOperation.handle(null);
    }

    @Test
    public void returnOperation_withNullFruit_Ok() {
        returnOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                null,
                EXPECTED));
    }

    @Test
    public void returnOperation_withEmptyFruit_Ok() {
        returnOperation.handle(Utils.createTransaction(FruitTransaction.Operation.BALANCE,
                EMPTY,
                EXPECTED));
    }

    @Test
    public void returnOperation_withNullTransaction_Ok() {
        returnOperation.handle(Utils.createTransaction(null,
                FRUIT,
                EXPECTED));
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }
}
