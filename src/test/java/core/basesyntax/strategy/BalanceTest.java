package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class BalanceTest {
    private static final Integer QUANTITY = 12;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new Balance();
        Storage.fruits.clear();
    }

    @Test
    public void handleFruitOperation_balance_ok() {
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        assertEquals(QUANTITY, Storage.get(FRUIT));
    }
}
