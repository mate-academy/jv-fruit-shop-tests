package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveStrategySupplyTest {
    private static SaveStrategySupply strategySupply;
    private static DaoServiceHashMap storage;
    private static DaoServiceHashMap expectedStorage;
    private static final Map<String, Integer> defaultStorageContent
            = Map.of("apple", 100, "banana", 50);
    private static final FruitTransaction transactionValid =
            new FruitTransaction("s","apple", 70);
    private static final FruitTransaction transactionNotExisting =
            new FruitTransaction("s","peer", 100);
    private static final FruitTransaction transactionNegativeQuantity =
            new FruitTransaction("s","banana", -100);
    private static final FruitTransaction transactionWrongType =
            new FruitTransaction("b","banana", 100);

    @BeforeClass
    public static void setup() {
        storage = new DaoServiceHashMap();
        expectedStorage = new DaoServiceHashMap();
        strategySupply = new SaveStrategySupply(storage);
    }

    @Before
    public void refreshStorage() {
        storage.setMemory(new HashMap<>(defaultStorageContent));
        expectedStorage.setMemory(new HashMap<>(defaultStorageContent));
    }

    @Test
    public void save_validTransaction_ok() {
        Integer expectedAmount = storage.getMemory().get(
                transactionValid.getFruit())
                + transactionValid.getQuantity();
        expectedStorage.getMemory().put(
                transactionValid.getFruit(),
                expectedAmount);
        strategySupply.save(transactionValid);
        Assert.assertEquals(storage.getMemory(), expectedStorage.getMemory());
    }

    @Test(expected = RuntimeException.class)
    public void save_withWrongType_notOk() {
        strategySupply.save(transactionWrongType);
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void save_withKeyNotExisting_notOk() {
        strategySupply.save(transactionNotExisting);
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void save_withValueNegative_notOk() {
        strategySupply.save(transactionNegativeQuantity);
        Assert.fail();
    }

    @Test(expected = NullPointerException.class)
    public void save_withValueNull_notOk() {
        strategySupply.save(null);
        Assert.fail();
    }
}
