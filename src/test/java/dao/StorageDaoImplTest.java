package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import db.Storage;
import java.util.Map;
import java.util.Set;
import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void update_correctFruit_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.update(apple, 10);
        int expected = 10;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_existingFruit_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 20);
        int expected = 20;
        int actual = storageDao.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_notExistingFruit_Ok() {
        Fruit apple = new Fruit("apple");
        Fruit orange = new Fruit("orange");
        Storage.dataBase.put(apple, 20);
        Integer actual = storageDao.get(orange);
        assertNull(actual);
    }

    @Test
    public void getAll_correctData_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 13);
        Storage.dataBase.put(new Fruit("banana"), 40);
        Set<Map.Entry<Fruit, Integer>> entries = storageDao.getAll();
        int actual = entries.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
