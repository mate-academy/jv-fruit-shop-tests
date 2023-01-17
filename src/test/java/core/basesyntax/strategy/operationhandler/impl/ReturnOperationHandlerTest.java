package core.basesyntax.strategy.operationhandler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 50;
    private static final int APPLE_RETURN_QUANTITY = 30;
    private static FruitTransaction transaction;
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void init() {
        transaction = new FruitTransaction();
        returnOperationHandler = new ReturnOperationHandler();
        FruitStorage.fruitStorage.put(APPLE, APPLE_QUANTITY);
        transaction.setFruit(APPLE);
        transaction.setQuantity(APPLE_RETURN_QUANTITY);
    }

    @Test
    public void handle_isOk() {
        returnOperationHandler.handle(transaction);
        int actual = FruitStorage.fruitStorage.get(APPLE);
        int expected = APPLE_QUANTITY + APPLE_RETURN_QUANTITY;
        assertEquals(expected, actual);
    }
}
