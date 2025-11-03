package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import java.util.NoSuchElementException;
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
        FruitStorage.clearStorage();
    }

    @Test
    void add_positiveQuantity_increaseQuantity() {
        fruitDao.add("apple", 15, Integer::sum);
        assertEquals(15, fruitDao.get("apple"));
    }

    @Test
    void add_tooManyNegativeQuantity_throwException() {
        assertThrows(RuntimeException.class,
                () -> fruitDao.add("banana", 20,
                        (oldValue, newValue) -> oldValue - newValue));
    }

    @Test
    void add_toAlreadyExistFruit_increaseQuantity() {
        fruitDao.add("apple", 3, Integer::sum);
        fruitDao.add("apple", 4, Integer::sum);

        assertEquals(7, fruitDao.get("apple"));
    }

    @Test
    void get_correctData_isOk() {
        fruitDao.add("banana", 20, Integer::sum);
        
        assertEquals(20, fruitDao.get("banana"));
    }

    @Test
    void get_noSuchData_throwException() {
        assertThrows(NoSuchElementException.class, () -> fruitDao.get("apple"));
    }

    @Test
    void getAll_copyStorage_isOk() {
        fruitDao.add("apple", 12, Integer::sum);
        fruitDao.add("banana", 13, Integer::sum);

        Map<String, Integer> expected = Map.of("apple", 12,
                "banana", 13);

        assertEquals(expected, fruitDao.getAll());
    }
}
