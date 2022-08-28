package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StorageDaoImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private FruitService fruitService = new FruitServiceImpl(storageDao);

    @Test
    public void update_OneFruit_Ok() {
        int fruitAmount = 100;
        storageDao.update("banana", fruitAmount);
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(fruitAmount, actual);
    }

    @Test
    public void update_TwoFruits_Ok() {
        storageDao.update("kiwi", 45);
        storageDao.update("orange", 55);
        int actualKiwi = Storage.fruitsStorage.get("kiwi");
        int actualOrange = Storage.fruitsStorage.get("orange");
        int expectedKiwi = 45;
        int expectedOrange = 55;
        assertEquals(expectedKiwi, actualKiwi);
        assertEquals(expectedOrange, actualOrange);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullValue_NotOk() {
        storageDao.update("banana", null);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullKey_NotOk() {
        storageDao.update(null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void update_NullData_NotOk() {
        storageDao.update(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void update_Negative_NotOk() {
        storageDao.update("orange", -1);
    }

    @Test
    public void update_ZeroAmount_Ok() {
        int expected = 0;
        storageDao.update("banana", 0);
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void getAmount_Ok() {
        int expected = 87;
        Storage.fruitsStorage.put("orange", 87);
        int actual = storageDao.getAmount("orange");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getAmount_DoesntExist_NotOk() {
        Storage.fruitsStorage.put("orange", 55);
        Storage.fruitsStorage.put("banana", 90);
        storageDao.getAmount("apple");
    }

    @After
    public void after() {
        Storage.fruitsStorage.clear();
    }
}
