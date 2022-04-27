package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionDaoImplTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static final String FIRST_FRUIT = "apple";
    private static final String SECOND_FRUIT = "banana";
    private static final String FIRST_INVALID_FRUIT = "%$#@!";
    private static final String SECOND_INVALID_FRUIT = "ma";
    private static final int FIRST_FRUIT_AMOUNT = 90;
    private static final int SECOND_FRUIT_AMOUNT = 152;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
    }

    @Test
    void dao_putElementsToStorage_Ok() {
        fruitTransactionDao.addToStorage("orange",20);
        fruitTransactionDao.addToStorage("grapes",15);
        fruitTransactionDao.addToStorage("watermelon",30);
        fruitTransactionDao.addToStorage("peach",50);
        Assertions.assertEquals(4, Storage.fruitTransactionStorage.size(),
                "Size of storage should be " + 4 + "but it is "
                        + Storage.fruitTransactionStorage.size());
        Assertions.assertEquals(Integer.valueOf(20), Storage.fruitTransactionStorage.get("orange"),
                "Element is not added correctly");
        Assertions.assertEquals(Integer.valueOf(15), Storage.fruitTransactionStorage.get("grapes"),
                "Element is not added correctly");
        Assertions.assertEquals(Integer.valueOf(30),
                Storage.fruitTransactionStorage.get("watermelon"),
                "Element is not added correctly");
        Assertions.assertEquals(Integer.valueOf(50), Storage.fruitTransactionStorage.get("peach"),
                "Element is not added correctly");
    }

    @Test
    void dao_putInvalidFruitName_NotOk() {
        assertThrows(RuntimeException.class,()
                -> fruitTransactionDao.addToStorage(FIRST_INVALID_FRUIT,15));
    }

    @Test
    void dao_putShortFruitName_NotOk() {
        assertThrows(RuntimeException.class,()
                -> fruitTransactionDao.addToStorage(SECOND_INVALID_FRUIT,15));
    }

    @Test
    void dao_putNullKey_not0k() {
        assertThrows(RuntimeException.class,()
                -> fruitTransactionDao.addToStorage(null,15));
    }

    @Test
    void dao_getValueByKeyFromStorage_Ok() {
        Storage.fruitTransactionStorage.put(FIRST_FRUIT,FIRST_FRUIT_AMOUNT);
        Storage.fruitTransactionStorage.put(SECOND_FRUIT,SECOND_FRUIT_AMOUNT);
        Integer actual = fruitTransactionDao.getFromStorage("apple");
        Integer expected = FIRST_FRUIT_AMOUNT;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void dao_getZeroByNotExistenceKeyFromStorage_Ok() {
        Storage.fruitTransactionStorage.put(SECOND_FRUIT,SECOND_FRUIT_AMOUNT);
        Integer actual = fruitTransactionDao.getFromStorage("apple");
        Integer expected = 0;
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void dao_getByNullKey_not0k() {
        assertThrows(RuntimeException.class,()
                -> fruitTransactionDao.getFromStorage(null));
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
