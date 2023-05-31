package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUpBeforeEach() {
        Storage.getCalculationMap().put("apple", 10);
        Storage.getCalculationMap().put("orange", 20);
    }

    @Test (expected = RuntimeException.class)
    public void getNullInputFruitName_NotOk() {
        storageDao.get(null);
    }

    @Test (expected = RuntimeException.class)
    public void getEmptyInputFruitName_NotOk() {
        storageDao.get("");
    }

    @Test
    public void getCorrectInputFruitName_Ok() {
        Integer actualAmount = storageDao.get("apple");
        assertEquals(Integer.valueOf(10), actualAmount);
    }

    @Test (expected = RuntimeException.class)
    public void putInputNullFruitName_NotOk() {
        String fruitName = null;
        Integer fruitAmount = 10;
        storageDao.put(fruitName, fruitAmount);
    }

    @Test (expected = RuntimeException.class)
    public void putEmptyInputFruitName_NotOk() {
        String fruitName = "";
        Integer fruitAmount = 10;
        storageDao.put(fruitName, fruitAmount);
    }

    @Test (expected = RuntimeException.class)
    public void putNullInputFruitAmount_NotOk() {
        String fruitName = "banana";
        Integer fruitAmount = null;
        storageDao.put(fruitName, fruitAmount);
    }

    @Test
    public void putCorrectInputFruitNameAndAmount_Ok() {
        String fruitName = "apple";
        Integer fruitAmount = 15;
        Integer fruitQuantityBeforePut = storageDao.get(fruitName);
        storageDao.put(fruitName, fruitAmount);
        Integer fruitQuantityAfterPut = storageDao.get(fruitName);
        assertFalse(fruitQuantityAfterPut
                .equals(fruitQuantityBeforePut));
        Storage.getCalculationMap().clear();
    }

    @Test
    public void getAll_Ok() {
        Map<String, Integer> allFruits = storageDao.getAll();
        assertEquals(2, allFruits.size());
        assertEquals(Integer.valueOf(10), allFruits.get("apple"));
        assertEquals(Integer.valueOf(20), allFruits.get("orange"));
    }

    @After
    public void tearDown() {
        Storage.getCalculationMap().clear();
    }
}
