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

    @Test
    public void update_addFruit_Ok() {
        Fruit orange = new Fruit("orange");
        storageDao.update(orange, 101);
        int expected = 101;
        int actual = Storage.dataBase.get(orange);
        assertEquals(expected, actual);
    }

    @Test
    public void get_existing_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 22);
        int expected = 22;
        int actual = storageDao.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_notExisting_NotOk() {
        Integer actual = storageDao.get(new Fruit("orange"));
        assertNull(actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_NotOk() {
        storageDao.get(null);
    }

    @Test
    public void addAll_Ok() {
        Storage.dataBase.put(new Fruit("apple"), 50);
        Storage.dataBase.put(new Fruit("orange"), 12);
        Storage.dataBase.put(new Fruit("banana"), 67);
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
