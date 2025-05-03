package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("mango");
        transaction = new Transaction(Transaction.Operation.BALANCE, fruit, 52);
    }

    @Test
    public void operation_applyBalance_ok() {
        boolean emptyStorage = Storage.storage.isEmpty();
        Assert.assertTrue(emptyStorage);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = transaction.getQuantity();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operation_applyNegativeBalance_notOk() {
        transaction.setQuantity(-1);
        operationHandler.apply(transaction);
        int expected = -1;
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
