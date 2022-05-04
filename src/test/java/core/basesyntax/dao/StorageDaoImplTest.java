package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }

    @Test
    public void update_AddFruit_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.update(apple, 10);
        int expected = 10;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void update_AddMore_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 20);
        storageDao.update(apple, 20);
        int expected = 40;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_Existing_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 20);
        int expected = 20;
        int actual = storageDao.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_NotExisting_NotOk() {
        Fruit apple = new Fruit("apple");
        Fruit orange = new Fruit("orange");
        Storage.dataBase.put(apple, 20);
        Integer actual = storageDao.get(orange);
        assertNull(actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_Null_NotOk() {
        storageDao.get(null);
    }

    @Test
    public void addAll_Correct_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 10);
        Storage.dataBase.put(new Fruit("orange"), 13);
        Storage.dataBase.put(new Fruit("banana"), 40);
        Set<Map.Entry<Fruit, Integer>> entries = storageDao.getAll();
        int actual = entries.size();
        int expected = 3;
        assertEquals(expected, actual);
    }
}
