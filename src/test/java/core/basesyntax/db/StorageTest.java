package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }

    @Test
    void addFruit_ShouldAddFruitToStorage() {
        Storage.addFruit("apple", 10);

        assertEquals(10, Storage.fruits.get("apple"));
    }

    @Test
    void addFruit_ShouldUpdateExistingFruitQuantity() {
        Storage.addFruit("apple", 10);
        Storage.addFruit("apple", 5);

        assertEquals(15, Storage.fruits.get("apple"));
    }

    @Test
    void removeFruit_ShouldRemoveFruitFromStorage() {
        Storage.addFruit("apple", 10);
        Storage.removeFruit("apple", 5);

        assertEquals(5, Storage.fruits.get("apple"));
    }

    @Test
    void removeFruit_ShouldThrowExceptionWhenNotEnoughFruit() {
        Storage.addFruit("apple", 10);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Storage.removeFruit("apple", 15);
        });

        assertEquals("Not enough apple in stock", thrown.getMessage());
    }
}
