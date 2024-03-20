package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new Return();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void handleFruitOperation_return_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.get(FRUIT));
    }

    @Test
    public void handleFruitOperation_return_whenFruitExists_ok() {
        Storage.fruits.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(Integer.valueOf(QUANTITY + QUANTITY), Storage.get(FRUIT));
    }
}
