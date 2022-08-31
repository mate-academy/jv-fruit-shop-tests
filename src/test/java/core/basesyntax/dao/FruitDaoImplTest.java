package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        Storage.fruits.clear();
        Storage.fruits.put("apple",150);
    }

    @Test
    public void addFruit_Ok() {
        fruitDao.addFruit("banana",500);
        int actual = Storage.fruits.size();
        int expected = 2;
        assertFalse(Storage.fruits.isEmpty());
        assertEquals(actual,expected);
    }

    @Test
    public void getQuantity_ValidData_Ok() {
        Integer expected = fruitDao.getQuantity("apple");
        Integer actual = 150;
        assertEquals(expected,actual);
    }

    @Test
    public void getQuantity_NotValidData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitDao.getQuantity("watermelon"));
    }

    @Test
    public void getAll_Ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("apple",150);
        expected.put("banana",500);
        Map<String, Integer> actual = fruitDao.getAll();
        assertEquals(actual, expected);
    }
}
