package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void clearStorage() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void add_fruit_Ok() {
        Fruit apple = new Fruit("apple", 20);
        storageDao.addFruit(apple);
        assertTrue(FruitShopStorage.storageFruits.contains(apple));
    }

    @Test
    public void get_fruit_Ok() {
        Fruit apple = new Fruit("apple", 20);
        storageDao.addFruit(apple);
        Fruit actual = storageDao.getFruit(apple.getName());
        assertEquals(apple, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_wrongFruits_notOk() {
        String wrongFruit = "pineapple";
        storageDao.getFruit(wrongFruit);
    }

    @Test
    public void getAll_nonEmptyStorage_Ok() {
        Fruit orange = new Fruit("orange", 40);
        Fruit peach = new Fruit("peach", 18);
        storageDao.addFruit(orange);
        storageDao.addFruit(peach);
        List<Fruit> testList = storageDao.getAll();
        assertTrue(testList.contains(orange));
        assertTrue(testList.contains(peach));
    }
}
