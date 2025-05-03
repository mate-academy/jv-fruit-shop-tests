package core.basesyntax.strategy.operation.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.operation.util.TransactionHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTest {
    private static Purchase purchase;
    private static TransactionHandler transactionHandler;
    private static Fruit banana;
    private static Transaction bananaPurchaseTransaction;

    @BeforeClass
    public static void setUp() {
        purchase = new Purchase();
        transactionHandler = new TransactionHandler();
        banana = new Fruit("banana");
        bananaPurchaseTransaction = new Transaction();
        bananaPurchaseTransaction.setFruit(banana);
        bananaPurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        bananaPurchaseTransaction.setSum(50);
    }

    @Before
    public void beforeAll() {
        Storage.storage.remove(banana);
    }

    @Test
    public void purchase_correctData_ok() {
        Integer expectedSum = 0;
        Storage.storage.put(banana, bananaPurchaseTransaction.getSum());
        purchase.apply(transactionHandler, bananaPurchaseTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(banana));
    }

    @Test
    public void purchase_incorrectData_notOk() {
        Storage.storage.put(banana, 50);
        Integer expectedSum = Storage.storage.get(banana);
        purchase.apply(transactionHandler, bananaPurchaseTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(banana));
    }

    @Test(expected = RuntimeException.class)
    public void purchase_outOfStock_notOk() {
        purchase.apply(transactionHandler, bananaPurchaseTransaction);
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}
