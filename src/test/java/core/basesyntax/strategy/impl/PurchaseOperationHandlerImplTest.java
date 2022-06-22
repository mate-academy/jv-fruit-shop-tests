package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.strategy.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static Operation purchaseHandle;
    private static Fruit testFruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandle = new PurchaseOperationHandlerImpl();
        testFruit = new Fruit("banana");
    }

    @Before
    public void setUp() {
        Storage.storage.put(testFruit, 200);
    }

    @Test
    public void changeBalancePurchase_ok() {
        int excepted = 100;
        TransactionInfo transactionInfo = new TransactionInfo("p", testFruit, 100);
        purchaseHandle.handle(transactionInfo);
        int actual = Storage.storage.get(testFruit);
        Assert.assertEquals(excepted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void changeBalancePurchase_notOk() {
        TransactionInfo transactionInfo = new TransactionInfo("p", testFruit, 300);
        purchaseHandle.handle(transactionInfo);
    }

    @Test(expected = RuntimeException.class)
    public void changeBalanceInvalidFruit_notOk() {
        testFruit = new Fruit("orange");
        TransactionInfo transactionInfo = new TransactionInfo("p", testFruit, 300);
        purchaseHandle.handle(transactionInfo);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
