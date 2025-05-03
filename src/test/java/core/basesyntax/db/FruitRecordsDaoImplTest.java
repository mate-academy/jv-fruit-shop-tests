package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordsDaoImplTest {
    private static FruitRecordsDao fruitRecordsDao;
    private static Fruit correctFruit;
    private static Fruit fruitWithNameNull;
    private static Fruit fruitAsNull;
    private static Integer quantity;
    private static Integer quantityAsNull;
    private Integer expected;
    private Integer actual;

    @BeforeClass
    public static void setUp() {
        fruitRecordsDao = new FruitRecordsDaoImpl();
        correctFruit = new Fruit("apple");
        fruitWithNameNull = new Fruit(null);
        fruitAsNull = null;
        quantity = 10;
        quantityAsNull = null;
    }

    @After
    public void clearResults() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void save_bothFruitAndQuantityNotNull_Ok() {
        fruitRecordsDao.save(correctFruit, quantity);
        expected = quantity;
        actual = FruitStorage.fruitStorage.get(correctFruit);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + correctFruit, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void save_bothFruitAndQuantityNull_NotOk() {
        fruitRecordsDao.save(fruitAsNull, quantityAsNull);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test(expected = RuntimeException.class)
    public void save_fruitNullAndQuantityNotNull_NotOk() {
        fruitRecordsDao.save(fruitAsNull, quantity);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test(expected = RuntimeException.class)
    public void save_fruitNotNullAndQuantityNull_NotOk() {
        fruitRecordsDao.save(correctFruit, quantityAsNull);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test(expected = RuntimeException.class)
    public void save_fruitWithNameNullAndQuantityNotNull_NotOk() {
        fruitRecordsDao.save(fruitWithNameNull, quantity);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test(expected = RuntimeException.class)
    public void save_fruitWithNameNullAndQuantityNull_NotOk() {
        fruitRecordsDao.save(fruitWithNameNull, quantityAsNull);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test
    public void getAll_getMapEqualsStorageMap_Ok() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(correctFruit, quantity);
        FruitStorage.fruitStorage.put(correctFruit, quantity);
        Map<Fruit, Integer> actual = fruitRecordsDao.getAll();
        assertEquals("Test failed! Actual storage not equals expected storage",
                expectedMap, actual);
    }

    @Test
    public void getFruitAmountFromStorage_correctFruit_Ok() {
        expected = FruitStorage.fruitStorage.get(correctFruit);
        actual = fruitRecordsDao.getFruitAmountFromStorage(correctFruit);
        assertEquals("Test failed! Returned quantity "
                + expected + ", but was "
                + actual + " for fruit " + correctFruit, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getFruitAmountFromStorage_fruitWithNameNull_Ok() {
        fruitRecordsDao.getFruitAmountFromStorage(fruitWithNameNull);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }

    @Test(expected = RuntimeException.class)
    public void getFruitAmountFromStorage_fruitNull_Ok() {
        fruitRecordsDao.getFruitAmountFromStorage(fruitAsNull);
        Assert.fail("Expected " + expected + ", but method ran successful.");
    }
}
