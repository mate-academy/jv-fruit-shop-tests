package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();

    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void addFruitToStorage_Ok() {
        Fruit fruit = new Fruit("orange");
        storageDao.add(fruit, 20);
        int expected = 20;
        int actual = FruitStorage.fruits.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void updateFruitWithNewQuantity_Ok() {
        Fruit fruit = new Fruit("orange");
        FruitStorage.fruits.put(fruit, 20);
        storageDao.update(fruit, 40);
        int expected = 40;
        int actual = FruitStorage.fruits.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void updateWithNotExistingFruit_Ok() {
        Fruit fruit = new Fruit("banana");
        storageDao.update(fruit, 50);
        int expected = 50;
        int actual = FruitStorage.fruits.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void getAmountOfExistingFruit_Ok() {
        Fruit fruit = new Fruit("apple");
        FruitStorage.fruits.put(fruit, 30);
        int expected = 30;
        int actual = storageDao.getAmount(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getAmountOfNotExistingFruit_NotOk() {
        Fruit addFruit = new Fruit("orange");
        Fruit getFruit = new Fruit("banana");
        FruitStorage.fruits.put(addFruit, 15);
        storageDao.getAmount(getFruit);
    }

    @Test
    public void getAllFruitsFromStorage_Ok() {
        Map<Fruit, Integer> testMap = new HashMap<>();
        testMap.put(new Fruit("kiwi"), 15);
        testMap.put(new Fruit("apple"), 30);
        testMap.put(new Fruit("orange"), 15);
        testMap.put(new Fruit("banana"), 25);

        FruitStorage.fruits.put(new Fruit("kiwi"), 15);
        FruitStorage.fruits.put(new Fruit("apple"), 30);
        FruitStorage.fruits.put(new Fruit("orange"), 15);
        FruitStorage.fruits.put(new Fruit("banana"), 25);

        Set<Map.Entry<Fruit, Integer>> expected = testMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }
}
