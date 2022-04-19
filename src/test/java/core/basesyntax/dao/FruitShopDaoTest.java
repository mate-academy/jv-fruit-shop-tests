package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FruitShopDaoTest {
    private static FruitShopDao dao;

    @BeforeClass
    public static void beforeClass() {
        dao = new FruitShopDaoImpl();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void addToStorage_properData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("banana", 220);

        dao.addToStorage("apple", 100);
        dao.addToStorage("banana", 220);

        Map<String, Integer> actual = Storage.fruits;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void addValue_properData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("orange", 40);

        dao.addToStorage("orange", 30);
        dao.addValue("orange", 10);

        Map<String, Integer> actual = Storage.fruits;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void subtractValue_properData_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("orange", 4);

        dao.addToStorage("orange", 10);
        dao.subtractValue("orange", 6);

        Map<String, Integer> actual = Storage.fruits;
        Assertions.assertEquals(actual, expected);
    }
}
