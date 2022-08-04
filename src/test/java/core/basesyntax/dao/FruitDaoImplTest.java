package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final Fruit apple = new Fruit("apple", 24);
    private static final Fruit banana = new Fruit("banana", 50);
    private static final Fruit coconut = new Fruit("coconut", 5);

    @BeforeClass
    public static void beforeClass() {
        fruitDao.add(apple);
        fruitDao.add(banana);
        fruitDao.add(coconut);
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
                + apple + ", actual: " + actualApple, apple, actualApple);
        assertEquals("Test fail! Expected fruit: " + banana
                + ", actual: " + actualBanana, banana, actualBanana);
        assertEquals("Test fail! Expected fruit: " + coconut
                + ", actual: " + actualCoconut, coconut, actualCoconut);
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
