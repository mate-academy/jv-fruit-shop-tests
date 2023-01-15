package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final FruitTransaction fruitTransaction = new FruitTransaction();
    private final PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    @Test
    public void apply_correctBalance_ok() {
        FruitDao.storage.put("mango", 25);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("mango");
        fruitTransaction.setQuantity(10);
        purchaseOperationHandler.apply(fruitTransaction);
        int expected = 15;
        int actual = FruitDao.getQuantity("mango");
        Assert.assertEquals("Quantity must be equals", expected, actual);
    }

    @After
    public void tearDown() {
        FruitDao.storage.clear();
    }
}