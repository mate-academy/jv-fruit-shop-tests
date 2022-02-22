package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static final PurchaseOperationHandlerImpl purchaseOperation =
            new PurchaseOperationHandlerImpl();

    @Before
    public void setUp() {
        Storage.fruitStorage.put("banana", 30);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void purchaseOperation_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        purchaseOperation.apply("banana", 10);
        assertEquals(expected.size(), Storage.fruitStorage.size());
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_InvalidFruitName() {
        purchaseOperation.apply(null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_InvalidQuantity() {
        purchaseOperation.apply("banana", -10);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_FruitIsNotExist() {
        purchaseOperation.apply("apple", 10);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_NotEnoughInStorage() {
        purchaseOperation.apply("banana", 40);
    }
}
