package core.basesyntax.strategy.operationhandler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 50;
    private static final int APPLE_SUPPLY_QUANTITY = 30;
    private static FruitTransaction transaction;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        transaction = new FruitTransaction();
        supplyOperationHandler = new SupplyOperationHandler();
        FruitStorage.fruitStorage.put(APPLE, APPLE_QUANTITY);
        transaction.setFruit(APPLE);
        transaction.setQuantity(APPLE_SUPPLY_QUANTITY);
    }

    @Test
    public void handle_supply_isOk() {
        supplyOperationHandler.handle(transaction);
        int actual = FruitStorage.fruitStorage.get(APPLE);
        int expected = APPLE_QUANTITY + APPLE_SUPPLY_QUANTITY;
        assertEquals(expected, actual);
    }
}
