package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        purchaseHandler = new PurchaseHandler();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void validPurchaseOperation_OK() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(3);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        secondFruitTransaction.setFruit("banana");
        secondFruitTransaction.setQuantity(3);
        Operation actual = purchaseHandler.proceed(fruitTransaction);
        Operation expected = purchaseHandler.proceed(secondFruitTransaction);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
