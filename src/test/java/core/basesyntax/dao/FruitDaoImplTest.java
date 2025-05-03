package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static FruitDaoImpl fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void add_ValidData_ok() {
        Map<String, Integer> expected = Map.of("apple",7,
                                            "banana", 5);
        fruitDao.add("apple", 7);
        fruitDao.add("banana", 5);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual);
    }

    @Test
    void update_ValidData_ok() {
        Storage.fruitStorage.put("apple",7);
        fruitDao.update("apple", 12);
        Map<String, Integer> expected = Map.of("apple", 12);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual);
    }

    @Test
    void update_InvalidData_throwsException() {
        Storage.fruitStorage.put("apple",7);
        assertThrows(NoSuchElementException.class, () ->
                fruitDao.update("banana", 10));
    }

    @Test
    void get_ValidData_Ok() {
        Storage.fruitStorage.put("apple", 7);
        int expected = 7;
        int actual = fruitDao.get("apple");
        assertEquals(expected, actual);
    }
}
