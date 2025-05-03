package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    public static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    public static final String ORANGE_NAME = "orange";
    public static final int ORANGE_QUANTITY = 1;
    public static final String BANANA_NAME = "banana";
    public static final int BANANA_QUANTITY = 2;
    private String nonExistFruit = "pomelo";
    private int newOrangeQuantity = 3;

    @BeforeAll
    public static void globalSetUp() {
        Storage.fruits.clear();
        STORAGE_DAO.add(ORANGE_NAME, ORANGE_QUANTITY);
        STORAGE_DAO.add(BANANA_NAME, BANANA_QUANTITY);
    }

    @Test
    void add_ok() {
        int expectedOrange = 1;
        int expectedBanana = 2;
        assertEquals(expectedOrange, Storage.fruits.get(ORANGE_NAME),
                "Add method doesn't add to storage product: " + ORANGE_NAME);
        assertEquals(expectedBanana, Storage.fruits.get(BANANA_NAME),
                "Add method doesn't add to storage product: " + BANANA_NAME);
    }

    @Test
    void add_sameFruit_ok() {
        int expected = 3;
        STORAGE_DAO.add(ORANGE_NAME, newOrangeQuantity);
        assertEquals(expected, STORAGE_DAO.get(ORANGE_NAME),
                "Method add doesn't update value");
    }

    @Test
    void get_ok() {
        int expectedQuantity = 2;
        assertEquals(expectedQuantity, STORAGE_DAO.get(BANANA_NAME),
                "Method get() return wrong quantity of " + BANANA_NAME);
    }

    @Test
    void contains_ok() {
        boolean expected = true;
        assertEquals(expected, STORAGE_DAO.contains(BANANA_NAME),
                "Method get can't find fruit");
    }

    @Test
    void contains_notExistentFruit_notOk() {
        boolean expected = false;
        assertEquals(expected, STORAGE_DAO.contains(nonExistFruit),
                "Method get find non exist fruit");
    }

    @Test
    void getAll_ok() {
        Set<Map.Entry<String, Integer>> expected = new HashSet<>();
        expected.add(new AbstractMap.SimpleEntry<>(ORANGE_NAME, ORANGE_QUANTITY));
        expected.add(new AbstractMap.SimpleEntry<>(BANANA_NAME, BANANA_QUANTITY));
        assertEquals(expected, STORAGE_DAO.getAll(),
                "Method getAll give wrong information about Storage");
    }
}
