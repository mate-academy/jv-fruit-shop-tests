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

public class BalanceOperationHandlerTest {
    private static Storage storage;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeClass
    public static void setUp() {
        storage = new StorageImpl();
        balanceOperationHandler = new BalanceOperationHandler(storage);
    }

    @Test
    public void balanceOperation_applyBalanceTransaction_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 40);
        balanceOperationHandler.applyOperation(fruitTransaction);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 40);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }
}
