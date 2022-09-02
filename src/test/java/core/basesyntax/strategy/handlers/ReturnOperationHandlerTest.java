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

public class ReturnOperationHandlerTest {
    private static Storage storage;
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storage = new StorageImpl();
        storage.add(new Fruit("banana"), 40);
        returnOperationHandler = new ReturnOperationHandler(storage);
    }

    @Test
    public void returnOperation_applyReturnOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 30);
        returnOperationHandler.applyOperation(fruitTransaction);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 70);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }
}
