package core.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.db.Storage;
import core.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 10;
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 20;
    private static final int EXPECTED_STORAGE_SIZE = 2;
    private static FruitTransaction fruitTransaction;
    private static OperationHandler balanceOparationHandler;

    @BeforeClass
    public static void init() {
        fruitTransaction = new FruitTransaction();
        balanceOparationHandler = new BalanceOperationHandler();
        Storage.fruits.put(APPLE, APPLE_QUANTITY);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(BANANA_QUANTITY);
    }

    @Test
    public void handle_Ok() {
        balanceOparationHandler.handle(fruitTransaction);
        int actualStorageSize = Storage.fruits.size();
        assertEquals(actualStorageSize, EXPECTED_STORAGE_SIZE);
        assertTrue(Storage.fruits.containsKey(BANANA));
        assertTrue(Storage.fruits.containsKey(APPLE));
        assertEquals((int) Storage.fruits.get(APPLE), APPLE_QUANTITY);
        assertEquals((int) Storage.fruits.get(BANANA), BANANA_QUANTITY);
    }
}
