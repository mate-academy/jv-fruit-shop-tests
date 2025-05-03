package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private static final int EMPTY_QUANTITY = 0;
    private static final String BALANCE_OPERATION = "b";
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        Storage.storage.put(TEST_FRUIT, EMPTY_QUANTITY);
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void setBalanceOperationHandler_OK() {
        Transaction transaction = new Transaction(BALANCE_OPERATION, TEST_FRUIT, 30);
        operationHandler.apply(transaction);
        int expected = 30;
        int actual = Storage.storage.get(TEST_FRUIT);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setBalanceOperation_New_Fruit_OK() {
        Fruit newFruit = new Fruit("apple");
        Transaction transaction = new Transaction(BALANCE_OPERATION, newFruit, 35);
        operationHandler.apply(transaction);
        Assert.assertTrue(Storage.storage.containsKey(newFruit));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
