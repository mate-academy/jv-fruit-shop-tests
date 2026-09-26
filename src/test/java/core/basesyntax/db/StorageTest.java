package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String FRUIT_NAME_BANANA = "banana";
    private static final String FRUIT_NAME_APPLE = "apple";
    private static final String FRUIT_NAME_ORANGE = "orange";
    private Storage storage;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        this.storage = new Storage();
    }

    @Test
    void getStorage_notEquals_ok() {
        storage.addToStorage(FRUIT_NAME_BANANA, 1);
        Map<String, Integer> first = storage.getStorage();
        storage.addToStorage(FRUIT_NAME_APPLE, 2);
        Map<String, Integer> second = storage.getStorage();

        assertNotEquals(first, second, "Getter should return a copy of the map");
    }

    @Test
    void addToStorage_validEntries_ok() {
        int expected = 1;

        storage.addToStorage(FRUIT_NAME_APPLE, expected);
        storage.addToStorage(FRUIT_NAME_ORANGE, expected);
        storage.addToStorage(FRUIT_NAME_BANANA, expected);

        assertEquals(expected, storage.getFromStorage(FRUIT_NAME_APPLE));
        assertEquals(expected, storage.getFromStorage(FRUIT_NAME_ORANGE));
        assertEquals(expected, storage.getFromStorage(FRUIT_NAME_BANANA));
    }

    @Test
    void addToStorage_setAnotherValue_ok() {
        storage.addToStorage(FRUIT_NAME_APPLE, 1);
        storage.addToStorage(FRUIT_NAME_APPLE, 2);
        storage.addToStorage(FRUIT_NAME_ORANGE, 1);
        storage.addToStorage(FRUIT_NAME_ORANGE, 2);
        storage.addToStorage(FRUIT_NAME_ORANGE, 3);
        storage.addToStorage(FRUIT_NAME_BANANA, 1);
        storage.addToStorage(FRUIT_NAME_BANANA, 2);
        storage.addToStorage(FRUIT_NAME_BANANA, 3);
        storage.addToStorage(FRUIT_NAME_BANANA, 4);

        assertEquals(2, storage.getFromStorage(FRUIT_NAME_APPLE));
        assertEquals(3, storage.getFromStorage(FRUIT_NAME_ORANGE));
        assertEquals(4, storage.getFromStorage(FRUIT_NAME_BANANA));
    }

    @Test
    void getFromStorage_nullKey_ok() {
        Integer actual = storage.getFromStorage(null);
        assertNull(actual);
    }

    @Test
    void getFromStorage_emptyList_ok() {
        Integer actual = storage.getFromStorage(FRUIT_NAME_BANANA);
        assertNull(actual);
    }
}
