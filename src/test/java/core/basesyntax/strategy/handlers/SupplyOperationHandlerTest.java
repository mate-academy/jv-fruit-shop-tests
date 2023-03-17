package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler handler = new SupplyOperationHandler();

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void apply_AddNewFruit_Ok() {
        handler.apply("apple", 5);
        assertEquals((Integer) 5, Storage.get("apple"));
    }

    @Test
    public void apply_AddToFruit_Ok() {
        Storage.put("banana", 10);
        handler.apply("banana", 5);
        assertEquals((Integer) 15, Storage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeQuantity_notOk() {
        handler.apply("banana", -5);
        fail("Should throw RuntimeException when we try to apply "
                + "SupplyOperationHandler with negative quantity");
    }
}
