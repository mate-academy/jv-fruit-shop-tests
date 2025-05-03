package core.basesyntax.strategy.operations;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private Fruit fruit;
    private Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("apple");
        transaction = new Transaction();
        transaction.setOperation(Transaction.Operation.PURCHASE);
        transaction.setFruit(fruit);
        transaction.setSum(0);
    }

    @Test
    public void purchase_validData_ok() {
        Storage.storage.put(fruit, 25);
        transaction.setSum(5);
        operationHandler.apply(transaction);
        Assert.assertEquals(Integer.valueOf(20), Storage.storage.get(fruit));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
