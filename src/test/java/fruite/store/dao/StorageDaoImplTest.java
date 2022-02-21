package fruite.store.dao;

import fruite.store.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class StorageDaoImplTest {
    StorageDaoImpl storageDao = new StorageDaoImpl();

    @After
    public void tearDown() throws Exception {
        Storage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void addFruitToStorage_quantityLessThanZero_notOk() {
        storageDao.addFruitToStorage("banana", -1);
    }

    @Test
    public void addFruitToStorage_validData_ok() {
        int sizeBeforeAddingData = Storage.fruitStorage.size();
        storageDao.addFruitToStorage("banana", 100);
        int sizeAfterAddingData = Storage.fruitStorage.size();
        Assert.assertNotEquals(sizeAfterAddingData, sizeBeforeAddingData);
    }

    @Test
    public void addValueByKey_validData_ok() {
        Integer initialQuantity = 100;
        Storage.fruitStorage.put("banana", initialQuantity);
        storageDao.addValueByKey("banana", 50);
        Assert.assertNotEquals(initialQuantity, Storage.fruitStorage.get("banana"));
    }

    @Test
    public void subtractValueByKey_validData_ok() {
        Integer initialQuantity = 100;
        Storage.fruitStorage.put("banana", initialQuantity);
        storageDao.subtractValueByKey("banana", 50);
        Assert.assertNotEquals(initialQuantity, Storage.fruitStorage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void subtractValueByKey_substractedValueIsLessThabZero_notOk() {
        Storage.fruitStorage.put("banana", 100);
        storageDao.subtractValueByKey("banana", 200);
    }
}