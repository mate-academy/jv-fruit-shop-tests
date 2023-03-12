package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

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

    @Test
    public void getNullInputFruitName_NotOk() {
        try {
            storageDao.get(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeExeption should be thrown for null input fruit name");
    }

    @Test
    public void getEmptyInputFruitName_NotOk() {
        try {
            storageDao.get("");
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeExeption should be thrown for null input fruit name");
    }

    @Test
    public void getCorrectInputFruitName_Ok() {
        Integer actualAmount = storageDao.get("apple");
        assertEquals(Integer.valueOf(10), actualAmount);
    }

    @Test
    public void putInputNullFruitName_NotOk() {
        String fruitName = null;
        Integer fruitAmount = 10;

        try {
            storageDao.put(fruitName, fruitAmount);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeExeption should be thrown for null input fruitName name "
                + fruitName);
    }

    @Test
    public void putEmptyInputFruitName_NotOk() {
        String fruitName = "";
        Integer fruitAmount = 10;

        try {
            storageDao.put(fruitName, fruitAmount);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeExeption should be thrown for epty input fruitName name "
                + fruitName);
    }

    @Test
    public void putNullInputFruitAmount_NotOk() {
        String fruitName = "banana";
        Integer fruitAmount = null;

        try {
            storageDao.put(fruitName, fruitAmount);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeExeption should be thrown for null input amount "
                + fruitAmount);
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
