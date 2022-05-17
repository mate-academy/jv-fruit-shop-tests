package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    private static StorageDao storageDao;
    private Fruit fruit;
    private int amount;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
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
    public void getAll_checkStorage_Ok() {
        Storage.fruits.put(fruit, amount);
        Storage.fruits.put(new Fruit("banana"), 40);
        Map<Fruit, Integer> actual = storageDao.getAll();
        Map<Fruit, Integer> expected = new HashMap<>(2);
        expected.put(new Fruit("apple"), 22);
        expected.put(new Fruit("banana"), 40);
        assertEquals(expected, actual);
    }
}
