package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    private static StorageDao storageDao;
    private static StorageService storageService;
    private Fruit fruit;
    private int amount;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        storageService = new StorageServiceImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("apple");
        amount = 22;
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void add_checkFruitAndAmountInStorage_Ok() {
        storageDao.add(fruit, amount);
        assertTrue(Storage.fruits.containsKey(fruit));
        Integer actual = Storage.fruits.get(fruit);
        Integer expected = 22;
        assertEquals(expected, actual);
    }

    @Test
    public void add_checkFruitInStorage_notOk() {
        storageDao.add(new Fruit("banana"), amount);
        assertFalse(Storage.fruits.containsKey(fruit));
    }

    @Test
    public void getAll_checkStorage_Ok() {
        storageService.add(fruit, amount);
        storageService.add(new Fruit("banana"), 22);
        storageService.add(new Fruit("banana"), 40);
        storageService.add(new Fruit("banana"), 40);
        Map<Fruit, Integer> actual = storageDao.getAll();
        Map<Fruit, Integer> expected = new HashMap<>(2);
        expected.put(new Fruit("apple"), 22);
        expected.put(new Fruit("banana"), 102);
        assertEquals(expected, actual);
    }
}
