package core.basesyntax.db;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
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
    void addFruit_addSingleFruit_isOk() {
        String fruitName = "apple";
        int quantity = 10;
        storage.addFruit(fruitName, quantity);
        int actual = storage.getFruitQuantity(fruitName);

        assertEquals(quantity,actual);
    }

    @Test
    void addFruit_addMultipleFruits_isOk() {
        storage.addFruit("apple", 10);
        storage.addFruit("banana", 20);
        storage.addFruit("cherry", 30);

        assertEquals(10, storage.getFruitQuantity("apple"));
        assertEquals(20, storage.getFruitQuantity("banana"));
        assertEquals(30, storage.getFruitQuantity("cherry"));
    }

    @Test
    void addFruit_updateExistingFruit_isOk() {
        String fruitName = "apple";
        int initialQuantity = 10;
        int updatedQuantity = 20;

        storage.addFruit(fruitName, initialQuantity);
        storage.addFruit(fruitName, updatedQuantity);

        assertEquals(updatedQuantity, storage.getFruitQuantity(fruitName));
    }

    @Test
    void getFruitQuantity_nonExistentFruit_returnsNull() {
        assertNull(storage.getFruitQuantity("nonexistent"));
    }

    @Test
    void getFruits_validData_isOk() {
        storage.addFruit("apple", 10);
        storage.addFruit("banana", 20);
        storage.addFruit("cherry", 30);

        Map<String, Integer> fruits = storage.getFruits();

        assertEquals(3, fruits.size());
        assertTrue(fruits.containsKey("apple"));
        assertTrue(fruits.containsKey("banana"));
        assertTrue(fruits.containsKey("cherry"));
        assertEquals(10, fruits.get("apple"));
        assertEquals(20, fruits.get("banana"));
        assertEquals(30, fruits.get("cherry"));
    }

    @Test
    void clear_clearStorage_isOk() {
        storage.addFruit("apple", 10);
        storage.addFruit("banana", 20);
        storage.addFruit("cherry", 30);
        storage.clear();
        assertTrue(storage.getFruits().isEmpty());
    }
}
