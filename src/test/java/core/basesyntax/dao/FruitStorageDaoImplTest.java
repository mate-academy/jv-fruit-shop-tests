package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FruitStorageDaoImplTest {
    private FruitStorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new FruitStorageDaoImpl();
        storageDao.add("apple", 50);
        storageDao.add("orange", 40);
        storageDao.add("lemon", 15);
    }

    @Test
    void getFruitByValidName_ok() {
        int expectedFruitApple = 50;
        int expectedFruitLemon = 15;
        int actualFruitApple = storageDao.getFruitQuantity("apple");
        int actualFruitLemon = storageDao.getFruitQuantity("lemon");
        assertEquals(expectedFruitApple, actualFruitApple);
        assertEquals(expectedFruitLemon, actualFruitLemon);
    }

    @Test
    void getFruitByInvalidName_notOk() {
        assertThrows(NoSuchElementException.class, ()
                -> storageDao.getFruitQuantity("noName"));
    }

    @Test
    void getAllFruits_ok() {
        Map<String, Integer> expected = Map.of(
                "apple", 50,
                "orange", 40,
                "lemon", 15);
        Map<String, Integer> actual = storageDao.getAllFruits();
        assertEquals(expected, actual);
    }

    @Test
    void getTotalFruitQuantity_ok() {
        int expected = 105;
        int actual = storageDao.calculateTotalQuantity();
        assertEquals(expected, actual);
    }

    @AfterEach
    void clearStorage() {
        storageDao.getAllFruits().clear();
    }
}