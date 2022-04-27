package core.basesyntax.dao;

import core.basesyntax.database.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionDaoImplTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static final String FIRST_FRUIT = "apple";
    private static final String SECOND_FRUIT = "banana";
    private static final String FIRST_INVALID_FRUIT = "%$#@!";
    private static final String SECOND_INVALID_FRUIT = "ma";
    private static final int FIRST_FRUIT_AMOUNT = 90;
    private static final int SECOND_FRUIT_AMOUNT = 152;

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
    }

    @Test
    public void addToStorage_putElements_Ok() {
        fruitTransactionDao.addToStorage("orange",20);
        fruitTransactionDao.addToStorage("grapes",15);
        fruitTransactionDao.addToStorage("watermelon",30);
        fruitTransactionDao.addToStorage("peach",50);
        Assert.assertEquals("Size of storage should be " + 4 + " but it is "
                        + Storage.fruitTransactionStorage.size(), 4,
                Storage.fruitTransactionStorage.size());
        Assert.assertEquals("Element is not added correctly.",
                Integer.valueOf(20), Storage.fruitTransactionStorage.get("orange"));
        Assert.assertEquals("Element is not added correctly.",
                Integer.valueOf(15), Storage.fruitTransactionStorage.get("grapes"));
        Assert.assertEquals("Element is not added correctly.",
                Integer.valueOf(30), Storage.fruitTransactionStorage.get("watermelon"));
        Assert.assertEquals("Element is not added correctly",
                Integer.valueOf(50), Storage.fruitTransactionStorage.get("peach"));
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_putInvalidFruitName_NotOk() {
        fruitTransactionDao.addToStorage(FIRST_INVALID_FRUIT,15);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_putShortFruitName_NotOk() {
        fruitTransactionDao.addToStorage(SECOND_INVALID_FRUIT,15);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_putNullKey_not0k() {
        fruitTransactionDao.addToStorage(null,15);
    }

    @Test
    public void getFromStorage_getValueByKey_Ok() {
        Storage.fruitTransactionStorage.put(FIRST_FRUIT,FIRST_FRUIT_AMOUNT);
        Storage.fruitTransactionStorage.put(SECOND_FRUIT,SECOND_FRUIT_AMOUNT);
        Integer actual = fruitTransactionDao.getFromStorage("apple");
        Integer expected = FIRST_FRUIT_AMOUNT;
        Assert.assertEquals("Wrong amount.Element added not correctly.",expected, actual);
    }

    @Test
    public void getFromStorage_getZeroByNotExistenceKey_Ok() {
        Storage.fruitTransactionStorage.put(SECOND_FRUIT,SECOND_FRUIT_AMOUNT);
        Integer actual = fruitTransactionDao.getFromStorage("apple");
        Integer expected = 0;
        Assert.assertEquals("Wrong amount.Element added not correctly.",expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void getFromStorage_getByNullKey_not0k() {
        fruitTransactionDao.getFromStorage(null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
