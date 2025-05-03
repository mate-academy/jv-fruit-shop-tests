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

public class SupplyOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new SupplyOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("mango");
        Storage.storage.put(fruit, 45);
        transaction = new Transaction(Transaction.Operation.SUPPLY, fruit, 10);
    }

    @Test
    public void operation_applySupply_notOk() {
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = 55;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operation_applyNegativeSupply_notOk() {
        transaction.setQuantity(-1);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = 44;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
