package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoTest {
    private static Fruit banana;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        banana = new Fruit("Banana");
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void add_validInputData_Ok() {
        storageDao.add(banana, 10);
        int expected = 10;
        int actual = Storage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_validInputDataToElementAlreadyInStorage_Ok() {
        Storage.fruitStorage.put(banana, 10);
        storageDao.add(banana, 20);
        int expected = 30;
        int actual = Storage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_validInputData_SizeIncrease() {
        storageDao.add(banana, 10);
        int size = Storage.fruitStorage.size();
        Assert.assertEquals(1, size);
    }

    @Test
    public void add_validInputDataInExcitingStorageSizeSame_Ok() {
        Storage.fruitStorage.put(banana, 15);
        storageDao.add(banana, 10);
        int size = Storage.fruitStorage.size();
        Assert.assertEquals(1, size);
    }

    @Test
    public void remove_validInputDataAlreadyInStorage_Ok() {
        Storage.fruitStorage.put(banana, 10);
        storageDao.remove(banana, 5);
        int expected = 5;
        int actual = Storage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_validElementAlreadyInStorage_Ok() {
        Storage.fruitStorage.put(banana, 10);
        int expected = 10;
        int actual = storageDao.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll_checkDataFromStorage_Ok() {
        Storage.fruitStorage.put(banana, 10);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(banana, 10);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storageDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
