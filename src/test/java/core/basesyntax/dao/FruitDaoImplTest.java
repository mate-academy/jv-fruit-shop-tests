package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private Fruit apple;
    private Fruit kiwi;
    private Storage storage;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        storage = new StorageImpl();
        fruitDao = new FruitDaoImpl();
        fruitDao.put(apple, 10);
        fruitDao.put(kiwi, 15);
    }

    @Test
    public void putMethodTest_Ok() {
        int expectedSize = 2;
        assertEquals(expectedSize, storage.getStorage().size());
    }

    @Test()
    public void getAmountCurrentFruitInShopMethodTest_Ok() {
        int expectedAmount = 15;
        int actualAmount = fruitDao.getAmountCurrentFruitInShop(kiwi);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void getAmountNoCurrentFruitTest_NotOk() {
        storage.getStorage().clear();
        fruitDao.getAmountCurrentFruitInShop(kiwi);
    }

    @Test
    public void updateMethodTest_Ok() {
        int expectedAmount = 33;
        fruitDao.update(apple, 33);
        int actualAmount = fruitDao.getAmountCurrentFruitInShop(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void updateWithNothingToTest_Ok() {
        storage.getStorage().clear();
        fruitDao.update(apple, 33);
    }

    @Test
    public void remove() {
        assertNull(null, fruitDao.remove());
    }

    @After
    public void deleteAll() {
        storage.getStorage().clear();
    }
}
