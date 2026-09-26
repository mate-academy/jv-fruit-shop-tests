package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsDaoImplTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private FruitsDao dao;

    @BeforeEach
    void setUp() {
        this.dao = new FruitsDaoImpl();
    }

    @Test
    void getAll_notEquals_ok() {
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 1);
        Map<String, Integer> first = dao.getAll();
        dao.addFruitToStorage(FRUIT_NAME_APPLE, 2);
        Map<String, Integer> second = dao.getAll();

        assertNotEquals(first, second, "Getter should return a copy of the map");
    }

    @Test
    void addFruitToStorage_validEntries_ok() {
        int expected = 1;

        dao.addFruitToStorage(FRUIT_NAME_APPLE, expected);
        dao.addFruitToStorage(FRUIT_NAME_ORANGE, expected);
        dao.addFruitToStorage(FRUIT_NAME_BANANA, expected);

        assertEquals(expected, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(expected, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
        assertEquals(expected, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
    }

    @Test
    void addToStorage_setAnotherValue_ok() {
        dao.addFruitToStorage(FRUIT_NAME_APPLE, 1);
        dao.addFruitToStorage(FRUIT_NAME_APPLE, 2);
        dao.addFruitToStorage(FRUIT_NAME_ORANGE, 1);
        dao.addFruitToStorage(FRUIT_NAME_ORANGE, 2);
        dao.addFruitToStorage(FRUIT_NAME_ORANGE, 3);
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 1);
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 2);
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 3);
        dao.addFruitToStorage(FRUIT_NAME_BANANA, 4);

        assertEquals(2, dao.getFruitFromStorage(FRUIT_NAME_APPLE));
        assertEquals(3, dao.getFruitFromStorage(FRUIT_NAME_ORANGE));
        assertEquals(4, dao.getFruitFromStorage(FRUIT_NAME_BANANA));
    }

    @Test
    void getFromStorage_nullKey_ok() {
        Integer actual = dao.getFruitFromStorage(null);
        assertNull(actual);
    }

    @Test
    void getFromStorage_emptyList_ok() {
        Integer actual = dao.getFruitFromStorage(FRUIT_NAME_BANANA);
        assertNull(actual);
    }
}
