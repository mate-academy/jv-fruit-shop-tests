package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private static final int TEST_QUANTITY = 20;
    private static final String RETURN_OPERATION = "r";
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        Storage.storage.put(TEST_FRUIT, TEST_QUANTITY);
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void setBalanceOperationHandler_OK() {
        Transaction transaction = new Transaction(RETURN_OPERATION, TEST_FRUIT, 30);
        operationHandler.apply(transaction);
        int expected = 50;
        int actual = Storage.storage.get(TEST_FRUIT);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
