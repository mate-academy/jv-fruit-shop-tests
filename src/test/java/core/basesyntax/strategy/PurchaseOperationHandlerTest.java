package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private static final int TEST_QUANTITY = 50;
    private static final String PURCHASE_OPERATION = "p";
    private OperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        Storage.storage.put(TEST_FRUIT, TEST_QUANTITY);
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void setPurchaseOperationHandler_OK() {
        Transaction purchaseTransaction = new Transaction(PURCHASE_OPERATION, TEST_FRUIT, 25);
        purchaseOperationHandler.apply(purchaseTransaction);
        int expected = 25;
        int actual = Storage.storage.get(TEST_FRUIT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void setPurchaseOperation_notOK() {
        Transaction purchaseTransaction = new Transaction(PURCHASE_OPERATION, TEST_FRUIT, 55);
        purchaseOperationHandler.apply(purchaseTransaction);

    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
