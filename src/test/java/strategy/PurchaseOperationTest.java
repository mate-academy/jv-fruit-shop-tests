package strategy;

import db.Database;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.impl.PurchaseOperationImpl;

public class PurchaseOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationImpl();
    }

    @Before
    public void setUp() {
        Database.database.put("apple", 20);
    }

    @Test
    public void purchaseOperation_EnoughFruits_OK() {
        FruitTransaction transaction =
                new FruitTransaction(
                        FruitTransaction.Operation.getByValue("p"),
                        "apple", 15);
        int storeQuantity = Database.database.get(transaction.getFruit());
        Database.database.put(transaction.getFruit(),
                operationHandler.operationHandler(transaction, storeQuantity));
        int expected = 5;
        int actual = Database.database.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_NotEnoughFruits_NotOK() {
        FruitTransaction transaction =
                new FruitTransaction(
                        FruitTransaction.Operation.getByValue("p"),
                        "apple", 21);
        int storeQuantity = Database.database.get(transaction.getFruit());
        Database.database.put(transaction.getFruit(),
                operationHandler.operationHandler(transaction, storeQuantity));
    }

    @After
    public void tearDown() {
        Database.database.clear();
    }
}
