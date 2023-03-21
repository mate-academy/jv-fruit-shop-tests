package core.basesyntax.strategy.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveStrategyPurchaseTest {
    private static SaveStrategyPurchase strategyPurchase;
    private static DaoServiceHashMap storage;
    private static DaoServiceHashMap expectedStorage;
    private static Map<String, Integer> defaultStorageContent
            = Map.of("apple", 100, "banana", 50);
    private static final FruitTransaction transactionValid =
            new FruitTransaction("p","apple", 70);
    private static final FruitTransaction transactionNotExisting =
            new FruitTransaction("p","peer", 100);
    private static final FruitTransaction transactionNegativeResult =
            new FruitTransaction("p","banana", 100);
    private static final FruitTransaction transactionNegativeQuantity =
            new FruitTransaction("p","banana", -100);
    private static final FruitTransaction transactionWrongType =
            new FruitTransaction("b","banana", 100);

    @BeforeClass
    public static void setup() {
        storage = new DaoServiceHashMap();
        expectedStorage = new DaoServiceHashMap();
        strategyPurchase = new SaveStrategyPurchase(storage);
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
                - transactionValid.getQuantity();
        expectedStorage.getMemory().put(
                transactionValid.getFruit(),
                expectedAmount);
        strategyPurchase.save(transactionValid);
        Assert.assertEquals(storage.getMemory(), expectedStorage.getMemory());
    }

    @Test(expected = RuntimeException.class)
    public void save_withWrongType_notOk() {
        strategyPurchase.save(transactionWrongType);
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void save_withKeyNotExisting_notOk() {
        strategyPurchase.save(transactionNotExisting);
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void save_withResultNegative_notOk() {
        strategyPurchase.save(transactionNegativeResult);
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void save_withValueNegative_notOk() {
        strategyPurchase.save(transactionNegativeQuantity);
        Assert.fail();
    }

    @Test(expected = NullPointerException.class)
    public void save_withValueNull_notOk() {
        strategyPurchase.save(null);
        Assert.fail();
    }
}
