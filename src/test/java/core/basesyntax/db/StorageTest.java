package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void addAndGet_ok() {
        storage.add("apple", 10);
        assertEquals(10, storage.get("apple"));
    }

    @Test
    void get_nonExistentFruit_returnsNull() {
        assertNull(storage.get("pear"));
    }

    @Test
    void clear_ok() {
        storage.add("banana", 5);
        storage.clear();
        assertNull(storage.get("banana"));
        assertEquals(0, storage.getAll().size());
    }
}
