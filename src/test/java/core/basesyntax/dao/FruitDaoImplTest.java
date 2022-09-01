package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);
    private static FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void fruitDao_addingNewFruit_ok() {
        fruitDao.add(TEST_FRUIT);
        int actualStorageSize = Storage.fruits.size();
        Assert.assertEquals(1, actualStorageSize);
        Fruit actualFruit = Storage.fruits.get(0);
        Assert.assertEquals(TEST_FRUIT, actualFruit);
    }

    @Test
    public void fruitDao_gettingFruitFromStorage_ok() {
        Storage.fruits.add(TEST_FRUIT);
        Fruit actual = fruitDao.getByName(TEST_FRUIT.getName()).get();
        Assert.assertEquals(TEST_FRUIT, actual);
    }

}
