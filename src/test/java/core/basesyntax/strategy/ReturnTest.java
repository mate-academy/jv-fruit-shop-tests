package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReturnTest {
    private static final int QUANTITY = 12;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new Return();
        Storage.fruits.clear();
    }

    @Test
    public void handleFruitOperation_return_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        Integer expected = QUANTITY;
        Integer current = Storage.get(FRUIT);
        assertEquals(expected, current);
    }
}
