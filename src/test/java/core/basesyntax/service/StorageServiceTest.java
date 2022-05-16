package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceTest {
    private static StorageService storageService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        storageService = new StorageServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void add_newFruit_Ok() {
        Fruit fruit = new Fruit("banana");
        int amount = 5;
        storageService.add(fruit, amount);
        assertTrue(storageDao.getAll().containsKey(fruit));
    }

    @Test
    public void add_sameFruit_Ok() {
        Fruit fruit = new Fruit("banana");
        int amount = 5;
        storageService.add(fruit, amount);
        Fruit fruit1 = new Fruit("banana");
        int amount1 = 50;
        storageService.add(fruit1, amount1);
        Integer expectedAmount = 55;
        Integer actualAmount = storageDao.getAll().get(fruit);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    public void get_validData_Ok() {
        Fruit fruit = new Fruit("apple");
        int amount = 5;
        storageService.add(fruit, amount);
        storageService.get(new Fruit("apple"), 5);
        assertTrue(storageDao.getAll().containsKey(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void get_data_NotOk() {
        storageService.get(new Fruit("banana"), 5);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_NotOk() {
        storageService.get(null, 5);
    }

    @Test
    public void update_validData_Ok() {
        Fruit fruit = new Fruit("apple");
        int amountAdd = 50;
        int amountGet = 5;
        storageService.add(fruit, amountAdd);
        storageService.get(new Fruit("apple"), amountGet);
        storageService.update(fruit, amountAdd - amountGet);
        Integer remnantActual = storageDao.getAll().get(fruit);
        Integer expected = 45;
        assertEquals(expected, remnantActual);
    }
}
