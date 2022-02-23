package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao = new FruitDaoImpl();

    @BeforeClass
    public static void prepareStorage() {
        Storage.fruitStorage.put("kiwi", 10);
        Storage.fruitStorage.put("pear", 25);
    }

    @Test
    public void add_ToStorage_Ok() {
        int storageSize = Storage.fruitStorage.size();
        fruitDao.add("banana", 10);
        fruitDao.add("apple",5);
        assertEquals(storageSize + 2, Storage.fruitStorage.size());
    }

    @Test
    public void get_FromStorage_Ok() {
        String nameExpectedFruit = "kiwi";
        Integer expectedValue = Storage.fruitStorage.get(nameExpectedFruit);
        assertEquals(expectedValue, fruitDao.get(nameExpectedFruit));
    }

    @Test
    public void get_AllData_Ok() {
        Map<String, Integer> actualMap = fruitDao.getStorageState();
        assertEquals(Storage.fruitStorage, actualMap);
    }

    @Test
    public void getStorageState_Ok() {
        String noExistFruit = "noExistFruit";
        assertNull(fruitDao.get(noExistFruit));
    }

    @Test
    public void get_DataFromEmptyStorage_Ok() {
        Storage.fruitStorage.clear();
        String someFruit = "someFruit";
        assertNull(fruitDao.get(someFruit));
    }

    @AfterClass
    public static void clearStorage() {
        Storage.fruitStorage.clear();
    }
}
