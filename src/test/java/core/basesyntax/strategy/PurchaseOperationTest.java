package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static Operation purchaseOperation;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperation();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
        Storage.data.put(new Fruit("apple"), 13);
    }

    @Test
    public void purchaseOperation_purchase_OK() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, fruit, 13);
        purchaseOperation.apply(transaction);
        int expected = 0;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_purchase_not_OK() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, fruit, 14);
        purchaseOperation.apply(transaction);
    }
}
