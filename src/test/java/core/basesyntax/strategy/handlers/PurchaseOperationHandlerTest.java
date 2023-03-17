package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String BANANA_FRUIT = "banana";
    private PurchaseOperationHandler handler = new PurchaseOperationHandler();

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_buyFruit_Ok() {
        Storage.put(BANANA_FRUIT, 10);
        handler.apply(BANANA_FRUIT, 5);
        assertEquals((Integer) 5, Storage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeQuantity_notOk() {
        handler.apply(BANANA_FRUIT, -5);
        fail("Should throw RuntimeException when we try to apply "
                + "PurchaseOperationHandler with negative quantity");
    }

    @Test(expected = RuntimeException.class)
    public void apply_notEnoughQuantity_notOk() {
        Storage.put(BANANA_FRUIT, 10);
        handler.apply(BANANA_FRUIT, 15);
        fail("Should throw RuntimeException when we try to apply "
                + "PurchaseOperationHandler with not enough quantity");
    }
}
