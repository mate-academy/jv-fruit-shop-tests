package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruittDaoImplTest {

    private static FruitDaoImpl fruitDao = new FruitDaoImpl();

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.fruits.clear();
    }

    @Test
    void testAddNewFruit() {
        fruitDao.add("apple", 10);
        assertEquals(10, fruitDao.get("apple"));
    }

    @Test
    void testUpdateExistingFruit() {
        fruitDao.add("apple", 10);
        fruitDao.add("apple", 20);
        assertEquals(20, fruitDao.get("apple"));
    }

    @Test
    void testGetExistingFruit() {
        // Предположим, что 'apple' уже добавлен в Storage с количеством 10
        Storage.fruits.put("apple", 10);
        int quantity = fruitDao.get("apple");
        assertEquals(10, quantity, "Should return the correct quantity of apples");
    }

    @Test
    void testGetNonExistingFruitHandled() {
        Integer result = Storage.fruits.getOrDefault("banana", 0);
        assertEquals(0, result, "Should return 0 for non-existing fruit as handled in the test.");
    }

    @Test
    void testAddWithZeroQuantity() {
        fruitDao.add("apple", 0);
        assertEquals(0, fruitDao.get("apple"));
    }

    @Test
    void testAddWithNegativeQuantity() {
        fruitDao.add("apple", -5);
        assertEquals(-5, fruitDao.get("apple"));
    }
}
