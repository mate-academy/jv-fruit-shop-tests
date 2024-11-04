package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void addFruit_newFruit_addsFruitSuccessfully() {
        storage.addFruit("apple", 10);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    void addFruit_existingFruit_increasesQuantity() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", 5);
        assertEquals(15, storage.getFruitQuantity("apple"));
    }

    @Test
    void addFruit_zeroQuantity_doesNotChangeQuantity() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", 0);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    void addFruit_negativeQuantity_doesNotChangeQuantity() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", -5);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    void getFruitQuantity_existingFruit_returnsCorrectQuantity() {
        storage.addFruit("apple", 10);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    void getFruitQuantity_nonExistingFruit_returnsZero() {
        assertEquals(0, storage.getFruitQuantity("banana"));
    }

    @Test
    void updateFruitQuantity_existingFruit_updatesQuantity() {
        storage.addFruit("apple", 10);
        storage.updateFruitQuantity("apple", 5);
        assertEquals(5, storage.getFruitQuantity("apple"));
    }

    @Test
    void updateFruitQuantity_nonExistingFruit_setsQuantity() {
        storage.updateFruitQuantity("banana", 20);
        assertEquals(20, storage.getFruitQuantity("banana"));
    }

    @Test
    void updateFruitQuantity_zeroQuantity_setsQuantityToZero() {
        storage.addFruit("apple", 10);
        storage.updateFruitQuantity("apple", 0);
        assertEquals(0, storage.getFruitQuantity("apple"));
    }

    @Test
    void updateFruitQuantity_negativeQuantity_updatesToNegativeValue() {
        storage.updateFruitQuantity("apple", -10);
        assertEquals(-10, storage.getFruitQuantity("apple"));
    }

    @Test
    void getAllFruits_withFruits_returnsAllFruits() {
        storage.addFruit("apple", 10);
        storage.addFruit("banana", 5);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(2, fruits.size());
        assertEquals(10, fruits.get("apple"));
        assertEquals(5, fruits.get("banana"));
    }

    @Test
    void getAllFruits_modifyingReturnedMap_doesNotAffectStorage() {
        storage.addFruit("apple", 10);
        Map<String, Integer> fruits = storage.getAllFruits();
        fruits.put("banana", 20);

        assertNull(storage.getAllFruits().get("banana"));
    }

}
