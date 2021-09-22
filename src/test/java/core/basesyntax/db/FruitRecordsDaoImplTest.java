package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.After;
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

    @Test
    public void save_bothFruitAndQuantityNull_Ok() {
        fruitRecordsDao.save(fruitAsNull, quantityAsNull);
        expected = quantityAsNull;
        actual = FruitStorage.fruitStorage.get(fruitAsNull);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + fruitAsNull, expected, actual);
    }

    @Test
    public void save_fruitNullAndQuantityNotNull_Ok() {
        fruitRecordsDao.save(fruitAsNull, quantity);
        expected = quantity;
        actual = FruitStorage.fruitStorage.get(fruitAsNull);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + fruitAsNull, expected, actual);
    }

    @Test
    public void save_fruitNotNullAndQuantityNull_Ok() {
        fruitRecordsDao.save(correctFruit, quantityAsNull);
        expected = quantityAsNull;
        actual = FruitStorage.fruitStorage.get(correctFruit);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + correctFruit, expected, actual);
    }

    @Test
    public void save_fruitWithNameNullAndQuantityNotNull_Ok() {
        fruitRecordsDao.save(fruitWithNameNull, quantity);
        expected = quantity;
        actual = FruitStorage.fruitStorage.get(fruitWithNameNull);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + fruitWithNameNull, expected, actual);
    }

    @Test
    public void save_fruitWithNameNullAndQuantityNull_Ok() {
        fruitRecordsDao.save(fruitWithNameNull, quantityAsNull);
        expected = quantityAsNull;
        actual = FruitStorage.fruitStorage.get(fruitWithNameNull);
        assertEquals("Test failed! Storage must store "
                + expected + ", but was "
                + actual + " for fruit " + fruitWithNameNull, expected, actual);
    }

    @Test
    public void getAll_getMapEqualsStorageMap_Ok() {
        Map<Fruit, Integer> expected = FruitStorage.fruitStorage;
        Map<Fruit, Integer> actual = fruitRecordsDao.getAll();
        assertEquals("Test failed! Actual storage not equals expected storage", expected, actual);
    }

    @Test
    public void getFruitAmountFromStorage_correctFruit_Ok() {
        expected = FruitStorage.fruitStorage.get(correctFruit);
        actual = fruitRecordsDao.getFruitAmountFromStorage(correctFruit);
        assertEquals("Test failed! Returned quantity "
                + expected + ", but was "
                + actual + " for fruit " + correctFruit, expected, actual);
    }

    @Test
    public void getFruitAmountFromStorage_fruitWithNameNull_Ok() {
        expected = FruitStorage.fruitStorage.get(fruitWithNameNull);
        actual = fruitRecordsDao.getFruitAmountFromStorage(fruitWithNameNull);
        assertEquals("Test failed! Returned quantity "
                + expected + ", but was "
                + actual + " for fruit " + fruitWithNameNull, expected, actual);
    }

    @Test
    public void getFruitAmountFromStorage_fruitNull_Ok() {
        expected = FruitStorage.fruitStorage.get(fruitAsNull);
        actual = fruitRecordsDao.getFruitAmountFromStorage(fruitAsNull);
        assertEquals("Test failed! Returned quantity "
                + expected + ", but was "
                + actual + " for fruit " + fruitAsNull, expected, actual);
    }
}
