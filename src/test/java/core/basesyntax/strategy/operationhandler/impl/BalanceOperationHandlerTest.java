package core.basesyntax.strategy.operationhandler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int APPLE_QUANTITY = 40;
    private static final int BANANA_QUANTITY = 50;
    private static FruitTransaction transaction;
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void init() {
        transaction = new FruitTransaction();
        balanceOperationHandler = new BalanceOperationHandler();
        FruitStorage.fruitStorage.put(APPLE, APPLE_QUANTITY);
        transaction.setFruit(BANANA);
        transaction.setQuantity(BANANA_QUANTITY);
    }

    @Test
    public void handle_isOk() {
        balanceOperationHandler.handle(transaction);
        Map<String, Integer> actualFruitStorage = FruitStorage.fruitStorage;
        int actualSize = actualFruitStorage.size();
        int expectedSize = 2;
        assertEquals(actualSize, expectedSize);
        assertTrue(actualFruitStorage.containsKey(BANANA));
        assertEquals(BANANA_QUANTITY, (int) actualFruitStorage.get(BANANA));
        assertTrue(actualFruitStorage.containsKey(APPLE));
        assertEquals(APPLE_QUANTITY, (int) actualFruitStorage.get(APPLE));
    }
}
