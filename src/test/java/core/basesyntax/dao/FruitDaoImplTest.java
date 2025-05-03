package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit APPLE = new Fruit("apple", 24);
    private static final Fruit BANANA = new Fruit("banana", 50);
    private static final Fruit COCONUT = new Fruit("coconut", 5);
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitDao.add(APPLE);
        fruitDao.add(BANANA);
        fruitDao.add(COCONUT);
    }

    @Test
    public void add_correctStorageRespond_ok() {
        int actualStorageSize = Storage.fruits.size();
        int expectedStorageSize = 3;
        assertEquals("Test fail! Expected storage size: "
                + expectedStorageSize + ", actual size: "
                + actualStorageSize, expectedStorageSize, actualStorageSize);
    }

    @Test
    public void get_existingFruit_ok() {
        Fruit actualApple = fruitDao.get("apple");
        Fruit actualBanana = fruitDao.get("banana");
        Fruit actualCoconut = fruitDao.get("coconut");
        assertEquals("Test fail! Expected fruit: "
                + APPLE + ", actual: " + actualApple, APPLE, actualApple);
        assertEquals("Test fail! Expected fruit: " + BANANA
                + ", actual: " + actualBanana, BANANA, actualBanana);
        assertEquals("Test fail! Expected fruit: " + COCONUT
                + ", actual: " + actualCoconut, COCONUT, actualCoconut);
    }

    @Test(expected = RuntimeException.class)
    public void get_notExistingFruit_notOk() {
        fruitDao.get("lemon");
    }

    @Test
    public void getAll_equalSize_ok() {
        int actualSize = fruitDao.getAll().size();
        int expectedSize = Storage.fruits.size();
        assertEquals("Test fail! Expected list size: "
                + expectedSize + ", actual size: "
                + actualSize, expectedSize, actualSize);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
