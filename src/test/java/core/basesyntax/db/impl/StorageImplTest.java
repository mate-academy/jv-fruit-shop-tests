package core.basesyntax.db.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {

    private StorageImpl storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
    }

    @Test
    void getStorage_ShouldReturnCopyOfInternalStorage() {
        storage.addEntry("item1", 5);
        Map<String, Integer> returnedStorage = storage.getStorage();

        assertNotSame(storage.getStorage(), returnedStorage);
        assertEquals(1, returnedStorage.size());
        assertEquals(5, returnedStorage.get("item1"));
    }

    @Test
    void getQuantity_ShouldReturnCorrectQuantity() {
        storage.addEntry("item1", 10);

        assertEquals(10, storage.getQuantity("item1"));
    }

    @Test
    void getQuantity_ShouldReturnZeroForNonExistingKey() {
        assertEquals(0, storage.getQuantity("nonExistingItem"));
    }

    @Test
    void addEntry_ShouldAddNewEntry() {
        storage.addEntry("item1", 5);

        assertEquals(5, storage.getQuantity("item1"));
    }

    @Test
    void addEntry_ShouldUpdateExistingEntry() {
        storage.addEntry("item1", 5);
        storage.addEntry("item1", 10);

        assertEquals(10, storage.getQuantity("item1"));
    }

    @Test
    void removeEntry_ShouldRemoveExistingEntry() {
        storage.addEntry("item1", 5);
        storage.removeEntry("item1");

        assertEquals(0, storage.getQuantity("item1"));
        assertTrue(storage.getStorage().isEmpty());
    }

    @Test
    void removeEntry_ShouldDoNothingForNonExistingEntry() {
        storage.removeEntry("nonExistingItem");

        assertTrue(storage.getStorage().isEmpty());
    }

    @Test
    void addEntry_ShouldHandleNullValues() {
        storage.addEntry("item1", null);

        assertNull(storage.getStorage().get("item1"));
    }

    @Test
    void addEntry_ShouldThrowExceptionForNullKey() {
        assertThrows(NullPointerException.class, () -> storage.addEntry(null, 10));
    }
}
