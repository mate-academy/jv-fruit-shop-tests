package core.strategy;

import static org.junit.Assert.assertEquals;

import core.db.Storage;
import core.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_BALANCE = 10;
    private static final int APPLE_RETURN = 5;
    private static FruitTransaction fruitTransaction;
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void init() {
        fruitTransaction = new FruitTransaction();
        purchaseOperationHandler = new ReturnOperationHandler();
        fruitTransaction.setFruit(APPLE);
        Storage.fruits.put(APPLE, APPLE_BALANCE);
    }

    @Test
    public void handle_Ok() {
        fruitTransaction.setQuantity(APPLE_RETURN);
        purchaseOperationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(APPLE);
        int expected = APPLE_BALANCE + APPLE_RETURN;
        assertEquals(actual, expected);
    }
}
