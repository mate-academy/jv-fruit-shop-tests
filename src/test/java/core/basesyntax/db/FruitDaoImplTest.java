package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDaoImplTest {
    private FruitDaoImpl fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void updateFruitQuantity_AddNewFruit_Ok() {
        Integer result = fruitDao.updateFruitQuantity("apple", 10);
        assertNull(result);
        assertEquals(10, fruitDao.getAllFruits().get("apple"));
    }

    @Test
    void updateFruitQuantity_AddExistingFruit_Ok() {
        fruitDao.updateFruitQuantity("apple", 10);
        Integer result = fruitDao.updateFruitQuantity("apple", 5);
        assertEquals(10, result);
        assertEquals(15, fruitDao.getAllFruits().get("apple"));
    }

    @Test
    void updateFruitQuantity_NegativeQuantity_NotOk() {
        Exception exception = assertThrows(FruitDaoException.class, ()
                -> fruitDao.updateFruitQuantity("apple", -10));
        assertNotNull(exception);
    }

    @Test
    void updateFruitQuantity_InsufficientQuantity_NotOk() {
        fruitDao.updateFruitQuantity("apple", 5);
        Exception exception = assertThrows(FruitDaoException.class, ()
                -> fruitDao.updateFruitQuantity("apple", -10));
        assertNotNull(exception);
    }

    @Test
    void getAllFruits_NoFruits_Ok() {
        Map<String, Integer> fruits = fruitDao.getAllFruits();
        assertTrue(fruits.isEmpty());
    }

    @Test
    void getAllFruits_withFruits_Ok() {
        fruitDao.updateFruitQuantity("apple", 10);
        fruitDao.updateFruitQuantity("banana", 20);
        Map<String, Integer> fruits = fruitDao.getAllFruits();
        assertEquals(2, fruits.size());
        assertEquals(10, fruits.get("apple"));
        assertEquals(20, fruits.get("banana"));
    }
}
