package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoImplTest {
    private static final Map<String, Integer> TEST_FRUITS = Map.of(
            "apple", 50,
            "orange", 40,
            "lemon", 15);
    private static final String INVALID_NAME = "noName";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_LEMON = "lemon";
    private static final int APPLE_QUANTITY = 50;
    private static final int LEMON_QUANTITY = 15;
    private static final int TOTAL_FRUIT_QUANTITY = 105;
    private FruitStorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new FruitStorageDaoImpl();
        FruitStorage.fruitStorage.putAll(TEST_FRUITS);
    }

    @Test
    void getFruitByValidName_ok() {
        int actualFruitApple = storageDao.getFruitQuantity(FRUIT_APPLE);
        int actualFruitLemon = storageDao.getFruitQuantity(FRUIT_LEMON);
        assertEquals(APPLE_QUANTITY, actualFruitApple);
        assertEquals(LEMON_QUANTITY, actualFruitLemon);
    }

    @Test
    void getFruitByInvalidName_notOk() {
        assertThrows(NoSuchElementException.class, ()
                -> storageDao.getFruitQuantity(INVALID_NAME));
    }

    @Test
    void getAllFruits_ok() {
        Map<String, Integer> actual = storageDao.getAllFruits();
        assertEquals(TEST_FRUITS, actual);
    }

    @Test
    void getTotalFruitQuantity_ok() {
        int actual = storageDao.calculateTotalQuantity();
        assertEquals(TOTAL_FRUIT_QUANTITY, actual);
    }

    @AfterEach
    void clearStorage() {
        storageDao.getAllFruits().clear();
    }
}
