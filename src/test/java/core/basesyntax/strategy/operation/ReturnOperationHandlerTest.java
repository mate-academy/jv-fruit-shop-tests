package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final String EMPTY = "";
    private static final int QUANTITY = 20;
    private static FruitTransaction transaction;
    private static ReturnOperationHandler returnOperation;

    @BeforeClass
    public static void beforeAll() {
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT, QUANTITY);
        returnOperation = new ReturnOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void returnOperation_handlePut_Ok() {
        returnOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void returnOperation_handleAdd_Ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        returnOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        int expected = 40;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_notFoundInStorage_NotOk() {
        returnOperation.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_withNullFruit_NotOk() {
        returnOperation.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                null,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_withEmptyFruit_NotOk() {
        returnOperation.handle(new FruitTransaction(FruitTransaction.Operation.RETURN,
                EMPTY,
                QUANTITY));
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_withNullTransaction_NotOk() {
        returnOperation.handle(new FruitTransaction(null, FRUIT, QUANTITY));
    }
}
