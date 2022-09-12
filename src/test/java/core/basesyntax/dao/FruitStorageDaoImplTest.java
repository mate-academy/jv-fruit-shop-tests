package core.basesyntax.dao;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageDaoImplTest {
    private static final String FRUIT = "banana";
    private static final Integer AMOUNT = 20;
    private static final Integer AMOUNT_1 = 40;
    private FruitStorageDao fruitStorageDao;

    @Before
    public void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        Storage.fruitMap.put("banana", AMOUNT_1);
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void add_createNewFruit_OK() {
        String apple = "apple";
        fruitStorageDao.add(apple, AMOUNT);
        Assert.assertEquals(AMOUNT, Storage.fruitMap.get(apple));
    }

    @Test
    public void add_updateAmountFruit_OK() {
        Integer expectedAmount = 60;
        fruitStorageDao.add(FRUIT, AMOUNT);
        Assert.assertEquals(expectedAmount, Storage.fruitMap.get(FRUIT));
    }

    @Test(expected = RuntimeException.class)
    public void add_nullAmount_OK() {
        fruitStorageDao.add(FRUIT, null);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullFruit_notOK() {
        fruitStorageDao.add(null, AMOUNT);
        fail("You need check string name by null exception "
                + "and write message: Fruit couldn't be NULL");
    }

    @Test
    public void remove_OK() {
        Integer expectedAmount = 20;
        fruitStorageDao.remove(FRUIT, AMOUNT);
        Assert.assertEquals(expectedAmount, Storage.fruitMap.get(FRUIT));
    }

    @Test(expected = NoSuchElementException.class)
    public void remove_moreThanInStorage_notOK() {
        Integer amount = 60;
        fruitStorageDao.remove(FRUIT, amount);
        fail("We can't remove more fruit than current quantity, "
                + "you must create exception NoSuchElementException");
    }

    @Test
    public void getDataFromStorage_OK() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(FRUIT, AMOUNT_1);
        Map<String, Integer> actual = fruitStorageDao.getDataFromStorage();
        Assert.assertEquals(expected, actual);
    }
}
