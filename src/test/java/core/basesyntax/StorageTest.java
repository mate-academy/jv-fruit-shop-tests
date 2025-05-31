package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void fruitStorage_shouldBeEmptyInitially() {
        assertTrue(Storage.fruitStorage.isEmpty(), "Expected storage to be empty");
    }

    @Test
    void fruitStorage_shouldAllowAddingEntries() {
        Storage.fruitStorage.put("apple", 100);
        assertEquals(100, Storage.fruitStorage.get("apple"));
    }

    @Test
    void fruitStorage_shouldAllowUpdatingEntries() {
        Storage.fruitStorage.put("banana", 50);
        Storage.fruitStorage.put("banana", 75);
        assertEquals(75, Storage.fruitStorage.get("banana"));
    }

    @Test
    void fruitStorage_shouldReflectClearOperation() {
        Storage.fruitStorage.put("kiwi", 30);
        Storage.fruitStorage.clear();
        assertTrue(Storage.fruitStorage.isEmpty(), "Expected storage to be empty after clear");
    }

    @Test
    void fruitStorage_shouldReturnCorrectSize() {
        Storage.fruitStorage.put("a", 1);
        Storage.fruitStorage.put("b", 2);
        assertEquals(2, Storage.fruitStorage.size());
    }
}
