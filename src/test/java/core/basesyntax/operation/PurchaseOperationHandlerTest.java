package core.basesyntax.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitTransaction transaction;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        transaction = new FruitTransaction();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @Test
    public void handle_handlePurchaseOperation_Ok() {
        fruitDao.addFruit("plum", 50);
        fruitDao.addFruit("coconut", 5);
        transaction.setFruit("plum");
        transaction.setQuantity(25);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseOperationHandler.handle(transaction);
        int actual = fruitDao.getQuantity("plum");
        int expected = 25;
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void handle_handlePurchaseOperationWithEnoughItems_NotOk() {
        fruitDao.addFruit("coconut", 5);
        transaction.setFruit("coconut");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseOperationHandler.handle(transaction);
    }
}
