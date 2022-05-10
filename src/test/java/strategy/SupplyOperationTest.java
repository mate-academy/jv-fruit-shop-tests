package strategy;

import db.Database;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.impl.SupplyOperationImpl;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationImpl();
        Database.database.put("apple", 12);
    }

    @Test
    public void supplyOperation_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"),
                        "apple", 33);
        int storeQuantity = Database.database.get(transaction.getFruit());
        Database.database.put(transaction.getFruit(),
                operationHandler.operationHandler(transaction, storeQuantity));
        int expected = 45;
        int actual = Database.database.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Database.database.clear();
    }
}
