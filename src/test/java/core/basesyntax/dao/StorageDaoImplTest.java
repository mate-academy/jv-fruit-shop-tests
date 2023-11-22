package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;
    private Fruit apple;
    private Fruit banana;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    @After
    public void cleanUpStorage() {
        Storage.getStorage().clear();
    }

    @Test
    public void getQuantity_emptyStorage_Ok() {
        assertEquals(0, storageDao.getQuantity(banana));
        assertEquals(0, storageDao.getQuantity(apple));
    }

    @Test
    public void add_sameFruit_Ok() {
        storageDao.add(banana, 2);
        assertEquals(1, Storage.getStorage().size());

        storageDao.add(banana, 10);
        assertEquals(1, Storage.getStorage().size());
    }

    @Test
    public void add_differentFruits_Ok() {
        storageDao.add(banana, 2);
        assertEquals(1, Storage.getStorage().size());

        storageDao.add(apple, 5);
        assertEquals(2, Storage.getStorage().size());
    }

    @Test
    public void getQuantity_FruitPresent_Ok() {
        storageDao.add(banana, 10);
        assertEquals(10, storageDao.getQuantity(banana));

        storageDao.add(banana, 2);
        assertEquals(2, storageDao.getQuantity(banana));

        storageDao.add(apple, 1);
        assertEquals(1, storageDao.getQuantity(apple));

        storageDao.add(apple, 5);
        assertEquals(5, storageDao.getQuantity(apple));
    }
}
