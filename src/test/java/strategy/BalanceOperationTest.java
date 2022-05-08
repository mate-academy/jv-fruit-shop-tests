package strategy;

import db.Database;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.impl.BalanceOperationImpl;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationImpl();
    }

    @Test
    public void balanceOperation_RemnantsOfFruits_OK() {
        int storeQuantity = 0;
        FruitTransaction transaction =
                new FruitTransaction(
                        FruitTransaction.Operation.getByValue("b"),
                        "banana", 20);
        Database.database.put(transaction.getFruit(),
                operationHandler.operationHandler(transaction, storeQuantity));
        int expected = 20;
        int actual = Database.database.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Database.database.clear();
    }
}
