package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoTest {

    private static FruitDao dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitDao();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void setQuantity_Ok() {
        dao.setQuantity("test1", 2);
        assertEquals(Storage.fruits.getOrDefault("test1", 0), 2);
    }

    @Test
    void getQuantity_Ok() {
        Storage.fruits.put("test2", 157);
        int result = dao.getQuantity("test2");
        assertEquals(157, result);
    }

    @Test
    void getBalance_Ok() {
        Storage.fruits.clear();
        Storage.fruits.put("test31", 31);
        Storage.fruits.put("test32", 32);
        Map<String, Integer> result = dao.getBalance();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("test31", 31);
        expected.put("test32", 32);
        assertEquals(expected, result);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
