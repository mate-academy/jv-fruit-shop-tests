package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String APPLE_FRUIT = "apple";
    private static final String BANANA_FRUIT = "banana";
    private ReturnOperationHandler handler = new ReturnOperationHandler();

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_AddNewFruit_Ok() {
        handler.apply(APPLE_FRUIT, 5);
        assertEquals((Integer) 5, Storage.get(APPLE_FRUIT));
    }

    @Test
    public void apply_AddToFruit_Ok() {
        Storage.put(BANANA_FRUIT, 10);
        handler.apply(BANANA_FRUIT, 5);
        assertEquals((Integer) 15, Storage.get(BANANA_FRUIT));
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeQuantity_notOk() {
        handler.apply(BANANA_FRUIT, -5);
        fail("Should throw RuntimeException when we try to apply "
                + "ReturnOperationHandler with negative quantity");
    }
}
