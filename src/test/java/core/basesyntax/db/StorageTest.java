package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void putAndGet_ok() {
        Storage storage = new Storage();

        storage.put("banana", 20);

        assertEquals(20, storage.get("banana"));
    }

    @Test
    void getAll_ok() {
        Storage storage = new Storage();

        storage.put("apple", 10);
        storage.put("banana", 20);

        assertEquals(2, storage.getAll().size());
        assertEquals(10, storage.getAll().get("apple"));
        assertEquals(20, storage.getAll().get("banana"));
    }
}
