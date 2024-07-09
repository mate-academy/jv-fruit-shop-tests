package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String NAME_IN_LOWERCASE = "apple";
    private static final String NAME_IN_UPPERCASE = "APPLE";
    private static final String NAME_IN_MIXED_CASE = "ApPlE";
    private static final String EMPTY_NAME = "";
    private static final int NORMAL_QUANTITY = 10;
    private static final int NORMAL_AMOUNT = 5;
    private static final int AMOUNT_LESS_THAN_ZERO = -4;
    private static final int AMOUNT_ZERO = 0;
    private static StorageDao StorageDao;
    private static final Storage STORAGE = new Storage();

    @BeforeEach
    void setUp() {
        STORAGE.getFruits().clear();
        StorageDao = new StorageDaoImpl();
    }

    @Test
    void addFruit_EmptyName_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            StorageDao.addFruit(EMPTY_NAME, NORMAL_QUANTITY);
        });
    }

    @Test
    void addFruit_WithQuantityLessThanZero_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            StorageDao.addFruit(NAME_IN_LOWERCASE, AMOUNT_LESS_THAN_ZERO);
        });
    }

    @Test
    void addFruit_WithAllCorrectParameters_Ok() {
        StorageDao.addFruit(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        Fruit actual = StorageDao.getFruit(NAME_IN_LOWERCASE);
        Fruit expected = Fruit.of(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        assertEquals(expected, actual);
    }

    @Test
    void addFruit_WithQuantityZero_Ok() {
        StorageDao.addFruit(NAME_IN_LOWERCASE, AMOUNT_ZERO);
        Fruit actual = StorageDao.getFruit(NAME_IN_LOWERCASE);
        Fruit expected = Fruit.of(NAME_IN_LOWERCASE, AMOUNT_ZERO);
        assertEquals(expected, actual);
    }

    @Test
    void getFruit_AddByUpperCaseGetByMixedCase_Ok() {
        StorageDao.addFruit(NAME_IN_UPPERCASE, NORMAL_QUANTITY);
        Fruit actual = StorageDao.getFruit(NAME_IN_MIXED_CASE);
        Fruit expected = Fruit.of(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        assertEquals(expected, actual);
    }

    @Test
    void updateFruit_WithNormalAmount_Ok() {
        StorageDao.addFruit(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        StorageDao.update(NAME_IN_LOWERCASE, NORMAL_AMOUNT);
        int actual = StorageDao.getFruit(NAME_IN_LOWERCASE).getQuantity();
        int expected = NORMAL_AMOUNT;
        assertEquals(expected, actual);
    }

    @Test
    void updateFruit_WithNameInOtherCase_Ok() {
        StorageDao.addFruit(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        StorageDao.update(NAME_IN_MIXED_CASE, NORMAL_AMOUNT);
        int actual = StorageDao.getFruit(NAME_IN_LOWERCASE).getQuantity();
        int expected = NORMAL_AMOUNT;
        assertEquals(expected, actual);
    }

    @Test
    void updateFruit_NewFruit_Ok() {
        StorageDao.update(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        Fruit actual = StorageDao.getFruit(NAME_IN_LOWERCASE);
        Fruit expected = Fruit.of(NAME_IN_LOWERCASE, NORMAL_QUANTITY);
        assertEquals(expected, actual);
    }
}
