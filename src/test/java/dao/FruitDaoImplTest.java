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
import util.TestConstants;

class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put(TestConstants.APPLE, 120);
        Storage.fruitStock.put(TestConstants.BANANA, 65);
    }

    @Test
    void getBalance_validFruitName_isOk() {
        String fruitName = TestConstants.APPLE;
        int expectedQuantity = 120;
        int actualQuantity = fruitDao.getBalance(fruitName);
        Assertions.assertTrue(Storage.fruitStock.containsKey(fruitName));
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void getBalance_nonExistentFruitName_notOk() {
        String fruitName = TestConstants.ORANGE;
        Assertions.assertNull(fruitDao.getBalance(fruitName));
    }

    @Test
    void addBalance_newFruitName_isOk() {
        String fruitName = TestConstants.ORANGE;
        int expectedQuantity = 45;
        fruitDao.addBalance(fruitName, expectedQuantity);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertTrue(Storage.fruitStock.containsKey(fruitName));
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void updateBalance_existingFruit_isOk() {
        String fruitName = TestConstants.BANANA;
        int expectedQuantity = 70;
        fruitDao.updateBalance(fruitName, expectedQuantity);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertTrue(Storage.fruitStock.containsKey(fruitName));
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void getAllEntries_containsCorrectEntries_isOk() {
        Set<Map.Entry<String, Integer>> expected = Set.of(
                new AbstractMap.SimpleEntry<>(TestConstants.APPLE, 120),
                new AbstractMap.SimpleEntry<>(TestConstants.BANANA, 65));
        Set<Map.Entry<String, Integer>> actual = Storage.fruitStock.entrySet();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllEntries_wrongKey_entriesDoNotMatch_notOk() {
        Set<Map.Entry<String, Integer>> expected = Set.of(
                new AbstractMap.SimpleEntry<>(TestConstants.ORANGE, 120),
                new AbstractMap.SimpleEntry<>(TestConstants.BANANA, 65));
        Set<Map.Entry<String, Integer>> actual = Storage.fruitStock.entrySet();
        Assertions.assertNotEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
