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

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new PurchaseOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("mango");
        Storage.storage.put(fruit, 150);
        transaction = new Transaction(Transaction.Operation.PURCHASE, fruit, 50);
    }

    @Test
    public void operation_applyPurchase_ok() {
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = 100;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operation_applyPurchaseMoreThanPut_notOk() {
        transaction.setQuantity(151);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = 0;
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
