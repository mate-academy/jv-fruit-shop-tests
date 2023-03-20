package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FruitShopException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTest {
    private static final int QUANTITY = 12;
    private static final int INVALID_QUANTITY = 120;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new Purchase();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void handleFruitOperation_purchase_ok() {
        Storage.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, QUANTITY);
        Integer expected = QUANTITY - QUANTITY;
        assertEquals(expected, Storage.get(FRUIT));
    }

    @Test(expected = FruitShopException.class)
    public void handleFruitOperation_purchase_notOk() {
        Storage.put(FRUIT, QUANTITY);
        operationHandler.handleFruitOperation(FRUIT, INVALID_QUANTITY);
        fail("Expect: " + FruitShopException.class + " but is wasn't!");
    }
}
