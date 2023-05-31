package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void apply_correctPurchase_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        FruitDao.storage.put("mango", 25);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("mango");
        fruitTransaction.setQuantity(10);
        purchaseOperationHandler.apply(fruitTransaction);
        int expected = 15;
        int actual = FruitDao
                .getQuantity("mango");
        Assert.assertEquals("There are "
                + expected
                + " mango must be in storage ", expected, actual);
    }

    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }
}
