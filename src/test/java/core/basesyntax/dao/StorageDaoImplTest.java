package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.product.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    public void add_isOk() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 12);
        expected.put(new Fruit("apple"), 13);
        storageDao.add(new Fruit("apple"), 12);
        storageDao.add(new Fruit("apple"), 13);
        Map<Fruit, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void add_InvalidFruitOrAmount_isOk() {
        storageDao.add(new Fruit(null), 13);
        storageDao.add(null, 13);
        storageDao.add(new Fruit("banana"), -5);
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void add_SimilarFruits_isOk() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 12);
        expected.put(new Fruit("apple"), 12);
        storageDao.add(new Fruit("apple"), 12);
        storageDao.add(new Fruit("apple"), 12);
        Map<Fruit, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void get_isOk() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 5);
        Optional<Integer> expected = Optional.of(5);
        Optional<Integer> actual = storageDao.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void get_FromEmptyStorage_isOk() {
        Fruit apple = new Fruit("apple");
        Optional<Integer> expected = Optional.empty();
        Optional<Integer> actual = storageDao.get(apple);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_WithNullFruit_notOk() {
        storageDao.get(null);
    }

    @Test
    public void getAll_isOk() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 121);
        expected.put(new Fruit("apple"), 30);
        storageDao.add(new Fruit("banana"), 121);
        storageDao.add(new Fruit("apple"), 30);
        Map<Fruit,Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_FromEmptyStorage() {
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit,Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageDao.getAll().clear();
    }
}
