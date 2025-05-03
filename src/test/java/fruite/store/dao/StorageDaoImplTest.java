package fruite.store.dao;

import fruite.store.db.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private static final Integer INITIAL_QUANTITY = 100;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Before
    public void setUp() throws Exception {
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", INITIAL_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void addFruitToStorage_quantityLessThanZero_notOk() {
        storageDao.addFruitToStorage("apple", -1);
    }

    @Test(expected = RuntimeException.class)
    public void addFruitToStorage_nullInKeyOrQuantity_notOk() {
        storageDao.addFruitToStorage(null, null);
    }

    @Test
    public void addFruitToStorage_validData_ok() {
        int expected = Storage.fruitStorage.size();
        storageDao.addFruitToStorage("apple", 50);
        int actual = Storage.fruitStorage.size();
        Assert.assertNotEquals(actual, expected);
    }

    @Test
    public void addValueByKey_validData_ok() {
        storageDao.addValueByKey("banana", 50);
        Assert.assertNotEquals(INITIAL_QUANTITY, Storage.fruitStorage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void addValueByKey_nullInKeyOrQuantity_notOk() {
        storageDao.addValueByKey(null, null);
    }

    @Test
    public void subtractValueByKey_validData_ok() {
        storageDao.subtractValueByKey("banana", 50);
        Assert.assertNotEquals(INITIAL_QUANTITY, Storage.fruitStorage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void subtractValueByKey_recievedValueIsLessThanZero_notOk() {
        storageDao.subtractValueByKey("banana", 300);
    }

    @Test(expected = RuntimeException.class)
    public void substractValueByKey_nullInKeyOrQuantity_notOk() {
        storageDao.subtractValueByKey(null, null);
    }
}
