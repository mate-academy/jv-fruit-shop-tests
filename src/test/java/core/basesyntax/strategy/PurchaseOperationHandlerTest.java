package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperation;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
        Storage.data.put(new Fruit("apple"),10);
    }

    @Test
    public void purchaseOperation_purchase_ok() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction("p", fruit, 5);
        purchaseOperation.apply(transaction);
        int expected = 5;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
