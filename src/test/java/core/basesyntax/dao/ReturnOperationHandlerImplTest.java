package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static final ReturnOperationHandlerImpl returnOperationHandler =
            new ReturnOperationHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void returnHandler_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Map<String, Integer> initialState = new HashMap<>(Storage.fruitStorage);
        returnOperationHandler.apply("banana", 10);
        assertNotEquals(initialState.size(), Storage.fruitStorage.size());
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void returnHandler_InvalidFruitName() {
        returnOperationHandler.apply(null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void returnHandler_InvalidQuantity() {
        returnOperationHandler.apply("banana", -10);
    }
}
