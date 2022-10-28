package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.FruitStorage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStorageDaoImplTest {
    private static final String TEST_KEY = "banana";
    private static final Integer TEST_VALUE = 15;
    private static FruitStorageDao fruitStorageDao;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void modifyValueInDB_ok() {
        FruitStorage.fruitStorage.put(TEST_KEY, TEST_VALUE);
        int expected = 15;
        fruitStorageDao.modifyValue(TEST_KEY, expected);
        int actual = FruitStorage.fruitStorage.get(TEST_KEY);
        assertEquals(expected, actual);
    }

    @Test
    public void addEntryToDB_ok() {
        fruitStorageDao.addFruit(TEST_KEY, TEST_VALUE);
        boolean actual = FruitStorage.fruitStorage.isEmpty();
        assertFalse(actual);

    }

    @Test
    public void getValueByKey_ok() {
        FruitStorage.fruitStorage.put(TEST_KEY, TEST_VALUE);
        int actual = fruitStorageDao.get(TEST_KEY);
        int expected = TEST_VALUE;
        assertEquals(actual, expected);
    }

    @Test
    public void getValueByNoExistingKey_notOk() {
        FruitStorage.fruitStorage.put(TEST_KEY, TEST_VALUE);
        Integer actual = fruitStorageDao.get("Im not exist");
        assertNull(actual);
    }

    @Test
    public void getStorageWithValue_ok() {
        FruitStorage.fruitStorage.put(TEST_KEY, TEST_VALUE);
        FruitStorage.fruitStorage.put("peach", 37);
        int expected = 2;
        int actual = fruitStorageDao.getStorage().size();
        assertEquals(actual, expected);
    }
}
