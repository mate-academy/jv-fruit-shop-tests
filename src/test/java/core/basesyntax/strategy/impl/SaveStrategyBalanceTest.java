package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveStrategyBalanceTest {
    private static SaveStrategyBalance strategyBalance;
    private static DaoServiceHashMap storage;
    private static DaoServiceHashMap expectedStorage;
    private static final Map<String, Integer> defaultStorageContent
            = Map.of("apple", 100, "banana", 50);
    private static final FruitTransaction transactionValid =
            new FruitTransaction("b","peer", 100);
    private static final FruitTransaction transactionExisting =
            new FruitTransaction("b","banana", 100);
    private static final FruitTransaction transactionNegativeQuantity =
            new FruitTransaction("b","peer", -100);
    private static final FruitTransaction transactionWrongType =
            new FruitTransaction("s","peer", -100);

    @BeforeClass
    public static void setup() {
        storage = new DaoServiceHashMap();
        expectedStorage = new DaoServiceHashMap();
        strategyBalance = new SaveStrategyBalance(storage);
    }

    @Before
    public void refreshStorage() {
        storage.setMemory(new HashMap<>(defaultStorageContent));
        expectedStorage.setMemory(new HashMap<>(defaultStorageContent));
    }

    @Test
    public void save_validTransaction_ok() {
        expectedStorage.getMemory().put(
                transactionValid.getFruit(),
                transactionValid.getQuantity());
        strategyBalance.save(transactionValid);
        Assert.assertEquals(expectedStorage.getMemory(), storage.getMemory());
    }

    @Test(expected = RuntimeException.class)
    public void save_withWrongType_notOk() {
        strategyBalance.save(transactionWrongType);
    }

    @Test(expected = RuntimeException.class)
    public void save_withKeyExisting_notOk() {
        strategyBalance.save(transactionExisting);
    }

    @Test(expected = RuntimeException.class)
    public void save_withValueNegative_notOk() {
        strategyBalance.save(transactionNegativeQuantity);
    }

    @Test(expected = NullPointerException.class)
    public void save_withValueNull_notOk() {
        strategyBalance.save(null);
    }
}
