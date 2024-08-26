package dao;

import db.Storage;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put("apple", 120);
        Storage.fruitStock.put("banana", 65);
    }

    @Test
    void getBalance_validFruitName_isOk() {
        String expectedName = "apple";
        int expectedQuantity = 120;
        String actualName = Storage.fruitStock.keySet().stream()
                .filter(k -> k.equals(expectedName))
                .findFirst()
                .get();
        int actualQuantity = fruitDao.getBalance(actualName);
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void getBalance_nonExistentFruitName_notOk() {
        String expectedName = "orange";
        Assertions.assertNull(fruitDao.getBalance(expectedName));
    }

    @Test
    void addBalance_newFruitName_isOk() {
        String expectedName = "orange";
        int expectedQuantity = 45;
        Assertions.assertTrue(fruitDao.addBalance(expectedName, expectedQuantity));
        String actualName = Storage.fruitStock.keySet().stream()
                .filter(k -> k.equals(expectedName))
                .findFirst()
                .get();
        int actualQuantity = Storage.fruitStock.get(actualName);
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void updateBalance_existingFruit_isOk() {
        String expectedName = "banana";
        int expectedQuantity = 70;
        fruitDao.updateBalance(expectedName, expectedQuantity);
        String actualName = Storage.fruitStock.keySet().stream()
                .filter(k -> k.equals(expectedName))
                .findFirst()
                .get();
        int actualQuantity = Storage.fruitStock.get(actualName);
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void getAllEntries_containsCorrectEntries_isOk() {
        Set<Map.Entry<String, Integer>> expected = Set.of(
                new AbstractMap.SimpleEntry<>("apple", 120),
                new AbstractMap.SimpleEntry<>("banana", 65));
        Set<Map.Entry<String, Integer>> actual = Storage.fruitStock.entrySet();
        Assertions.assertTrue(expected.equals(actual));
    }

    @Test
    void getAllEntries_wrongKey_entriesDoNotMatch_notOk() {
        Set<Map.Entry<String, Integer>> expected = Set.of(
                new AbstractMap.SimpleEntry<>("orange", 120),
                new AbstractMap.SimpleEntry<>("banana", 65));
        Set<Map.Entry<String, Integer>> actual = Storage.fruitStock.entrySet();
        Assertions.assertFalse(expected.equals(actual));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
