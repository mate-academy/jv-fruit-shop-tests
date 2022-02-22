package core.basesyntax.dao;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    private static StorageDao storageDao;
    private Fruit defaultFruit;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUp() {
        defaultFruit = new Fruit("banana", 100);
    }

    @Test
    public void storageDao_add_Ok() {
        storageDao.add("banana", 100);
        Fruit expected = defaultFruit;
        Fruit actual = storage.get(0);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
        storageDao.add("banana", 100);
        expected.setAmount(200);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    public void storageDao_substract_Ok() {
        Fruit expected = new Fruit("banana", 90);
        storage.add(defaultFruit);
        storageDao.substract("banana", 10);
        Fruit actual = storage.get(0);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test(expected = RuntimeException.class)
    public void storageDao_substract_wrongAmount_notOk() {
        storage.add(defaultFruit);
        storageDao.substract("banana", 101);
    }

    @Test(expected = NoSuchElementException.class)
    public void storageDao_substract_notExists_notOk() {
        storageDao.substract("banana", 1);
    }

    @Test
    public void storageDao_get_Ok() {
        Fruit expected = defaultFruit;
        storage.add(defaultFruit);
        assertEquals(expected.getName(), storageDao.get("banana").getName());
        assertEquals(expected.getAmount(), storageDao.get("banana").getAmount());
    }

    @Test(expected = NoSuchElementException.class)
    public void storageDao_get_notOk() {
        storageDao.get("banana");
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
