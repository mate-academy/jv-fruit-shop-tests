package core.basesyntax.db.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopStorageTest {
    public static Storage storage;

    @BeforeEach
    void setUp() {
        storage = new ShopStorage();
    }

    @Test
    void getAll_returnsSameProducts_ok() {
        storage.update("banana", 10);
        storage.update("apple", 3);

        Map<String, Integer> expected = Map.of("banana", 10, "apple", 3);
        Map<String, Integer> actual = storage.getAll();

        assertEquals(expected, actual,
                "Storage getAll() does not return the same products");
    }

    @Test
    void getAll_emptyMap_returnsEmptyMap_ok() {
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = storage.getAll();

        assertEquals(expected, actual,
                "Storage getAll() does not return the empty map");
    }

    @Test
    void getAll_returnsUnmodifiableMap_ok() {
        storage.update("banana", 10);
        storage.update("apple", 3);

        Map<String, Integer> deepCopy = storage.getAll();

        assertThrows(UnsupportedOperationException.class, () -> deepCopy.put("kiwi", 44));
    }

    @Test
    void getByKey_AllValid_ok() {
        storage.update("grape", 2);

        int expected = 2;
        int actual = storage.getByKey("grape");

        assertEquals(expected, actual, "Returns wrong value");
    }

    @Test
    void getByKey_NotExistingProductKey_notOk() {
        assertThrows(RuntimeException.class, () -> storage.getByKey("invalid"));
    }

    @Test
    void getByKey_KeyIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> storage.getByKey(null));
    }

    @Test
    void update_AllValid_AddFunction_ok() {
        storage.update("plum", 99);

        Map<String, Integer> expected = Map.of("plum", 99);
        Map<String, Integer> actual = storage.getAll();

        assertEquals(expected, actual, "update() adds wrong data");
    }

    @Test
    void update_AllValid_UpdateFunction_ok() {
        storage.update("plum", 99);
        storage.update("plum", 1);

        Map<String, Integer> expected = Map.of("plum", 1);
        Map<String, Integer> actual = storage.getAll();

        assertEquals(expected, actual, "update() does not update the value");
    }

    @Test
    void update_keyIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> storage.update(null, 4));
    }

    @Test
    void update_valueIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> storage.update("peach", null));
    }

}