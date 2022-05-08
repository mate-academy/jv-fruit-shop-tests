package strategy;

import db.Database;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.impl.ReturnOperationImpl;

public class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationImpl();
    }

    @Before
    public void setUp() {
        Database.database.put("orange", 10);
    }

    @Test
    public void returnOperation_OK() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.getByValue("r"),
                        "orange", 5);
        int storeQuantity = Database.database.get(transaction.getFruit());
        Database.database.put(transaction.getFruit(),
                operationHandler.operationHandler(transaction, storeQuantity));
        int expected = 15;
        int actual = Database.database.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Database.database.clear();
    }
}
