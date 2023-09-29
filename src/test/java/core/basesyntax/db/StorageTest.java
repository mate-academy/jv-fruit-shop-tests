package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testFruitInventoryIsEmptyInitially() {
        assertTrue(storage.getFruitInventory().isEmpty());
    }

    @Test
    public void testAddToFruitInventory() {
        storage.getFruitInventory().put("apple", 10);
        storage.getFruitInventory().put("banana", 5);

        assertEquals(10, storage.getFruitInventory().get("apple"));
        assertEquals(5, storage.getFruitInventory().get("banana"));
    }

    @Test
    public void testRemoveFromFruitInventory() {
        storage.getFruitInventory().put("apple", 10);
        storage.getFruitInventory().put("banana", 5);

        storage.getFruitInventory().remove("apple");

        assertNull(storage.getFruitInventory().get("apple"));
        assertEquals(5, storage.getFruitInventory().get("banana"));
    }
}
