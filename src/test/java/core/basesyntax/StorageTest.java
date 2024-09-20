package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void addFruit_New_Fruit_Ok() {
        storage.addFruit("apple", 5);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(1, fruits.size());
        assertEquals(5, fruits.get("apple"));
    }

    @Test
    void addFruit_ExistingFruit_Ok() {
        storage.addFruit("apple", 5);
        storage.addFruit("apple", 3);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(1, fruits.size());
        assertEquals(8, fruits.get("apple"));
    }

    @Test
    void addFruit_RemoveFruit_Ok() {
        storage.addFruit("apple", 5);
        storage.removeFruit("apple", 2);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(3, fruits.get("apple"));
    }

    @Test
    void addFruit_RemoveFruit_Negative_NotOk() {
        storage.addFruit("apple", 5);

        assertThrows(RuntimeException.class,
                () -> storage.removeFruit("apple", 6),
                "Number of fruits can't be negative");
    }

    @Test
    void getAllFruits() {
        storage.addFruit("apple", 5);
        storage.addFruit("banana", 3);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(2, fruits.size());
        assertEquals(5, fruits.get("apple"));
        assertEquals(3, fruits.get("banana"));
    }
}
