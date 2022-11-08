package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Storage storage;
    private Fruit apple;
    private Fruit kiwi;

    @BeforeClass
    public static void globalSetUp() {
        storage = new StorageImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        fruitDao.put(apple, 10);
        fruitDao.put(kiwi, 15);
    }

    @Test
    public void putMethodTest_Ok() {
        int expectedSize = 2;
        assertEquals(expectedSize, storage.getStorage().size());
    }

    @Test()
    public void getMethodTest_Ok() {
        int expectedAmount = 15;
        int actualAmount = fruitDao.getAmountCurrentFruitInShop(kiwi);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void getAmountNoCurrentFruitTest_notOk() {
        storage.getStorage().clear();
        fruitDao.getAmountCurrentFruitInShop(kiwi);
    }

    @Test
    public void updateMethodTest_ok() {
        int expectedAmount = 33;
        fruitDao.update(apple, expectedAmount);
        int actualAmount = fruitDao.getAmountCurrentFruitInShop(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void updateWithNothingToTest_notOk() {
        int expectedAmount = 33;
        storage.getStorage().clear();
        fruitDao.update(apple, expectedAmount);
    }

    @After
    public void deleteAllDataFromStorage() {
        storage.getStorage().clear();
    }
}
