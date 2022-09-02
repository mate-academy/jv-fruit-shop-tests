package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static Storage storage;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storage = new StorageImpl();
        storage.add(new Fruit("banana"), 100);
        supplyOperationHandler = new SupplyOperationHandler(storage);
    }

    @Test
    public void supplyOperationHandler_applySupplyOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50);
        supplyOperationHandler.applyOperation(fruitTransaction);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 150);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }
}
