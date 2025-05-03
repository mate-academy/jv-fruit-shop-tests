package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitModel;
import core.basesyntax.storage.FruitStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDaoImpl storageDao;

    @Before
    public void setUp() throws Exception {
        storageDao = new StorageDaoImpl();
        FruitStorage.fruitStorage.put("apple", 10);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void getAmount_getAbsentKey_NotOk() {
        storageDao.getAmount("Bob_The_minion");
    }

    @Test(expected = RuntimeException.class)
    public void getAmount_keyToGetValueIsNull_NotOk() {
        String key = null;
        storageDao.getAmount(key);
    }

    @Test
    public void getAmount_getCorrectValue_Ok() {
        assertEquals(storageDao.getAmount("apple"), 10);
    }

    @Test(expected = RuntimeException.class)
    public void putFruitModel_fruitModelIsNull_NotOk() {
        FruitModel fruitModel = null;
        storageDao.putFruitModel(fruitModel);
    }

    @Test(expected = RuntimeException.class)
    public void putFruitModel_amountIsNegative_NotOk() {
        FruitModel fruitModel = new FruitModel("apple", -5);
        storageDao.putFruitModel(fruitModel);
    }

    @Test
    public void putFruitModel_testIfFruitModelWasAdded_Ok() {
        FruitModel fruitModel = new FruitModel("banana", 5);
        assertTrue(storageDao.putFruitModel(fruitModel));
        assertTrue(FruitStorage.fruitStorage.containsKey("banana")
                && FruitStorage.fruitStorage.get("banana") == 5);
    }

    @Test
    public void replaceWithNewAmount_replacedCorrect_Ok() {
        assertTrue(storageDao.replaceWithNewAmount("apple", 20));
        assertTrue(FruitStorage.fruitStorage.containsKey("apple")
                && FruitStorage.fruitStorage.get("apple") == 20);
    }

    @Test
    public void containsKey_doesContainsKey_Ok() {
        assertTrue(storageDao.containsKey("apple"));
    }

    @Test
    public void containsKey_keyIsAbsent_NotOk() {
        assertFalse(storageDao.containsKey("Stuart_the_minion"));
    }
}
