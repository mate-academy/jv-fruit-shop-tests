package core.basesyntax.dao.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static core.basesyntax.util.FruitTestConstants.BANANA;
import static core.basesyntax.util.FruitTestConstants.LEMON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void getStorage_AfterAddingFruitsReturnsCorrectData_Ok() {
        fruitDao.putToStorage(APPLE, 20);
        fruitDao.putToStorage(BANANA, 30);
        fruitDao.putToStorage(LEMON, 2);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(APPLE, 20);
        expected.put(BANANA, 30);
        expected.put(LEMON, 2);
        assertEquals(expected, fruitDao.getStorage(),
                "getStorage() should return all fruits and their quantities");
    }

    @Test
    void getStorage_InitialStorageReturnsEmpty_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        assertEquals(expected, fruitDao.getStorage(),
                "Storage should be empty initially");
    }

    @Test
    void getQualityByItemType_ExistingFruitReturnsQuantity_Ok() {
        fruitDao.putToStorage(APPLE, 20);
        assertEquals(20, fruitDao.getQualityByItemType(APPLE),
                "Should return the correct quantity for an existing fruit");
    }

    @Test
    void getQualityByItemType_NonExistingFruitReturnsZero_Ok() {
        int quantity = fruitDao.getQualityByItemType("nonExistingFruit");
        assertEquals(0, quantity, "Should return 0 for a non-existing fruit");
    }

    @Test
    void putToStorage_AddFruitsToStorage_Ok() {
        fruitDao.putToStorage(APPLE, 20);
        fruitDao.putToStorage(APPLE, 2);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(APPLE, 2);
        assertEquals(expected, Storage.fruitStorage,
                "Fruits should be added to the storage");
    }

    @Test
    void putToStorage_NegativeQuantity_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> fruitDao.putToStorage(APPLE, -20),
                "Putting fruit with negative quantity should throw IllegalArgumentException");
    }
}
