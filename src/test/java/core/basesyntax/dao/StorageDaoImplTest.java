package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static StorageDao storageDao;
    private static final String FRUIT = "banana";
    private static final int VALID_QUANTITY = 15;
    private static final int INVALID_QUANTITY = -15;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void addValidValues_Ok() {
        storageDao.add(FRUIT, VALID_QUANTITY);
        Assertions.assertEquals(VALID_QUANTITY, Storage.FRUITS.get(FRUIT));
    }

    @Test
    void addNullFruitName_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storageDao.add(null, VALID_QUANTITY));
    }

    @Test
    void addInvalidQuantity_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storageDao.add(FRUIT, INVALID_QUANTITY));
    }

    @Test
    void addZeroQuantity_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storageDao.add(FRUIT, 0));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
