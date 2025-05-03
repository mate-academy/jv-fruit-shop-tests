package core.strategy;

import static org.junit.Assert.assertEquals;

import core.db.Storage;
import core.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_BALANCE = 10;
    private static final int APPLE_PURCHASE = 5;
    private static final int APPLE_OVERBALANCE = 15;
    private static FruitTransaction fruitTransaction;
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void init() {
        fruitTransaction = new FruitTransaction();
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruitTransaction.setFruit(APPLE);
        Storage.fruits.put(APPLE, APPLE_BALANCE);
    }

    @Test
    public void handle_Ok() {
        fruitTransaction.setQuantity(APPLE_PURCHASE);
        purchaseOperationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(APPLE);
        int expected = APPLE_BALANCE - APPLE_PURCHASE;
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void handle_overBalance_notOk() {
        fruitTransaction.setQuantity(APPLE_OVERBALANCE);
        purchaseOperationHandler.handle(fruitTransaction);
    }
}
