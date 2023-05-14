package strategy.handler;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final String INVALID_FRUIT = "tomato";
    private static final int QUANTITY = 100;
    private static final int HALF_QUANTITY = 50;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler(new FruitDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void purchase_banana_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_BANANA, HALF_QUANTITY);
        Storage.fruits.put(FRUIT_BANANA, QUANTITY);
        purchaseOperationHandler.add(fruit);
        assertEquals((Integer) HALF_QUANTITY, Storage.fruits.get(FRUIT_BANANA));
    }

    @Test(expected = RuntimeException.class)
    public void purchase_tooMuch_banana_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_BANANA, HALF_QUANTITY * 10);
        Storage.fruits.put(FRUIT_BANANA, QUANTITY);
        purchaseOperationHandler.add(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_invalid_fruit_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                INVALID_FRUIT, HALF_QUANTITY);
        Storage.fruits.put(FRUIT_BANANA, QUANTITY);
        purchaseOperationHandler.add(fruit);
    }
}
