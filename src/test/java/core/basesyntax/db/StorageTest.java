package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void storage_putAndGet_ok() {
        Storage.fruits.put("banana", 100);
        assertEquals(100, Storage.fruits.get("banana"));
    }

    @Test
    void storage_clear_ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.clear();
        assertTrue(Storage.fruits.isEmpty());
    }
}
