package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private Storage storage;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        storage = new StorageImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storage);
    }

    @Test
    public void purchaseOperation_applyPurchaseOperation_OK() {
        storage.add(new Fruit("banana"), 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 30);
        purchaseOperationHandler.applyOperation(fruitTransaction);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 10);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }
}
