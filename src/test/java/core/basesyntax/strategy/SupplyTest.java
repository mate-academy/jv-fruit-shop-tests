package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class SupplyTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new Supply();
        Storage.fruits.clear();
    }

    @Test
    public void handleFruitOperation_supply_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.get(FRUIT));
    }

    @Test
    public void handleFruitOperation_return_whenFruitExists() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(Integer.valueOf(QUANTITY + QUANTITY), Storage.get(FRUIT));
    }
}
