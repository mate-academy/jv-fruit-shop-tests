package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveStrategyReturnTest {
    private static SaveStrategyReturn strategyReturn;
    private static DaoServiceHashMap storage;
    private static DaoServiceHashMap expectedStorage;
    private static final Map<String, Integer> defaultStorageContent
            = Map.of("apple", 100, "banana", 50);
    private static final FruitTransaction transactionValid =
            new FruitTransaction("r","apple", 70);
    private static final FruitTransaction transactionNotExisting =
            new FruitTransaction("r","peer", 100);
    private static final FruitTransaction transactionNegativeQuantity =
            new FruitTransaction("r","banana", -100);
    private static final FruitTransaction transactionWrongType =
            new FruitTransaction("b","banana", 100);

    @BeforeClass
    public static void setup() {
        storage = new DaoServiceHashMap();
        expectedStorage = new DaoServiceHashMap();
        strategyReturn = new SaveStrategyReturn(storage);
    }

    @Before
    public void refreshStorage() {
        storage.setMemory(new HashMap<>(defaultStorageContent));
        expectedStorage.setMemory(new HashMap<>(defaultStorageContent));
    }

    @Test
    public void save_validTransaction_ok() {
        final Integer expectedAmount = storage.getMemory().get(
                transactionValid.getFruit())
                + transactionValid.getQuantity();
        expectedStorage.getMemory().put(
                transactionValid.getFruit(),
                expectedAmount);
        strategyReturn.save(transactionValid);
        Assert.assertEquals(expectedStorage.getMemory(), storage.getMemory());
    }

    @Test(expected = RuntimeException.class)
    public void save_withWrongType_notOk() {
        strategyReturn.save(transactionWrongType);
    }

    @Test(expected = RuntimeException.class)
    public void save_withKeyNotExisting_notOk() {
        strategyReturn.save(transactionNotExisting);
    }

    @Test(expected = RuntimeException.class)
    public void save_withValueNegative_notOk() {
        strategyReturn.save(transactionNegativeQuantity);
    }

    @Test(expected = NullPointerException.class)
    public void save_withValueNull_notOk() {
        strategyReturn.save(null);
    }
}
