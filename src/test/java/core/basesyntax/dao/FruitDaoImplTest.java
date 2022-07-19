package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        fruitDao = new FruitDaoImpl();
        fruitDao.add(new Fruit("banana", 0));
        fruitDao.add(new Fruit("peach", 50));
        fruitDao.add(new Fruit("lemon", 24));
    }

    @Test
    public void add_SizeOfStorageIncreased_Ok() {
        fruitDao.add(new Fruit("kiwi", 4));
        int expectedSize = 4;
        int actualSize = Storage.fruits.size();
        Assert.assertEquals("Test failed! Expected to get: " + expectedSize + " but was: "
                + actualSize, expectedSize, actualSize);
    }

    @Test
    public void get_ExactFruit_Ok() {
        String expectedFruitName = "banana";
        String actualFruitName = fruitDao.get("banana").getFruitName();
        Assert.assertEquals("Test failed! Expected to get: " + expectedFruitName
                + " but was: " + actualFruitName, expectedFruitName, actualFruitName);
    }

    @Test
    public void update_FruitQuantityChanged_Ok() {
        Fruit expectedFruit = new Fruit("banana", 20);
        int expectedFruitQuantity = expectedFruit.getQuantity();
        fruitDao.update(expectedFruit);
        Fruit actualFruit = fruitDao.get(expectedFruit.getFruitName());
        int actualFruitQuantity = actualFruit.getQuantity();
        Assert.assertEquals("Test failed! Expected to get: " + expectedFruitQuantity
                + " but was: " + actualFruitQuantity, expectedFruitQuantity, actualFruitQuantity);
    }

    @Test
    public void getAll_SizeCorrespondsToStorageSize_Ok() {
        int expectedSize = Storage.fruits.size();
        int actualSize = fruitDao.getAll().size();
        Assert.assertEquals("Test fail! Expected to get: "
                + expectedSize + " but was " + actualSize, expectedSize, actualSize);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
