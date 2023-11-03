package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitStorageDaoImplTest {
    private static FruitStorageDaoImpl dao;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitStorageDaoImpl();
    }

    @Test
    void add_simpleTest_ok() {
        dao.add("apple", 10);
        assertEquals(10, dao.getQuantity("apple"));
    }

    @Test
    void add_zeroQuantity_ok() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.add("banana", 0));
    }

    @Test
    void add_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                dao.add("banana", -5));
    }

    @Test
    void add_largeQuantity_ok() {
        dao.add("banana", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, dao.getQuantity("banana"));
    }

    @Test
    void getQuantity_simpleTest_ok() {
        dao.add("apple", 5);
        int quantity = dao.getQuantity("apple");
        assertEquals(5, quantity);
    }

    @Test
    void add_nullName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.add(null, 5));
    }

    @Test
    void add_emptyName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dao.add("", 5));
    }

    @Test
    void getAll_simpleTest_ok() {
        dao.add("banana", 15);
        dao.add("apple", 7);
        Map<String, Integer> allFruits = dao.getAll();
        assertEquals(15, allFruits.get("banana"));
        assertEquals(7, allFruits.get("apple"));
    }

    @Test
    void getAll_withEmptyStorage_ok() {
        Map<String, Integer> allFruits = dao.getAll();
        assertTrue(allFruits.isEmpty());
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
