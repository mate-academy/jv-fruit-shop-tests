package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static final BalanceOperationHandlerImpl balanceOperationHandler =
            new BalanceOperationHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void apply_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Map<String, Integer> initialState = new HashMap<>(Storage.fruitStorage);
        balanceOperationHandler.apply("banana", 10);
        assertNotEquals(initialState.size(), Storage.fruitStorage.size());
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void apply_invalidFruitName_notOk() {
        balanceOperationHandler.apply(null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void apply_invalidQuantity_notOk() {
        balanceOperationHandler.apply("banana", -10);
    }
}
