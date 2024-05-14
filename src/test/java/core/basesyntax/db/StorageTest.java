package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void setFruitsQuantity_setNewFruitQuantity_ok() {
        storage.setFruitsQuantity("apple", 50);
        assertEquals(50, storage.getFruitsInventory().get("apple"));
    }

    @Test
    void increaseFruitsQuantity_increaseExistingFruitQuantity_ok() {
        storage.setFruitsQuantity("banana", 30);
        storage.increaseFruitsQuantity("banana", 20);
        assertEquals(50, storage.getFruitsInventory().get("banana"));
    }

    @Test
    void decreaseFruitsQuantity_decreaseExistingFruitQuantity_ok() {
        storage.setFruitsQuantity("orange", 40);
        storage.decreaseFruitsQuantity("orange", 10);
        assertEquals(30, storage.getFruitsInventory().get("orange"));
    }

    @Test
    void decreaseFruitsQuantity_decreaseNonExistingFruitQuantity_notOk() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> storage.decreaseFruitsQuantity("grape", 10));
        assertEquals("Not enough fruits in inventory",
                runtimeException.getMessage());
    }

    @Test
    void setFruitsQuantity_negativeQuantity_notOk() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> storage.setFruitsQuantity("apple", -5));
        assertEquals("Quantity cannot be negative",
                runtimeException.getMessage());
    }

    @Test
    void increaseFruitsQuantity_negativeResult_notOk() {
        storage.setFruitsQuantity("apple", 5);
        var runtimeException = assertThrows(RuntimeException.class,
                () -> storage.increaseFruitsQuantity("apple", -1));
        assertEquals("Quantity cannot be negative",
                runtimeException.getMessage());
    }
}
