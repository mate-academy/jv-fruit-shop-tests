package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @AfterEach
    void clearStorage() {
        Storage.clear();
    }

    @Test
    void add_newFruit_ok() {
        Storage.add("apple", 10);
        assertEquals(10, Storage.getAll().get("apple"));
    }

    @Test
    void add_existingFruit_ok() {
        Storage.add("apple", 10);
        Storage.add("apple", 20);
        assertEquals(30, Storage.getAll().get("apple"));
    }

    @Test
    void subtract_existingFruit_ok() {
        Storage.add("apple", 10);
        Storage.subtract("apple", 5);
        assertEquals(5, Storage.getAll().get("apple"));
    }

    @Test
    void subtract_moreThanAvailable_ok() {
        Storage.add("apple", 10);
        Storage.subtract("apple", 15);
        assertEquals(0, Storage.getAll().get("apple"));
    }

    @Test
    void subtract_nonExistingFruit_ok() {
        Storage.subtract("apple", 10);
        assertEquals(0, Storage.getAll().get("apple"));
    }

    @Test
    void update_existingFruit_ok() {
        Storage.add("apple", 10);
        Storage.update("apple", 100);

        assertEquals(100, Storage.getAll().get("apple"));
    }

    @Test
    void clear_storageCleared_ok() {
        Storage.add("apple", 10);
        Storage.add("banana", 5);
        Storage.clear();
        assertTrue(Storage.getAll().isEmpty());
    }
}
