package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.getInventory().clear();
    }

    @Test
    void add_newFruit_success() {
        Storage.add("apple", 10);
        Map<String, Integer> inventory = Storage.getInventory();
        assertEquals(10, inventory.get("apple"));
    }

    @Test
    void add_existingFruit_quantityUpdated() {
        Storage.add("banana", 5);
        Storage.add("banana", 3);
        Map<String, Integer> inventory = Storage.getInventory();
        assertEquals(8, inventory.get("banana"));
    }

    @Test
    void remove_existingFruit_quantityDecreased() {
        Storage.add("orange", 10);
        Storage.remove("orange", 4);
        Map<String, Integer> inventory = Storage.getInventory();
        assertEquals(6, inventory.get("orange"));
    }

    @Test
    void remove_moreThanAvailable_negativeQuantity() {
        Storage.add("grape", 5);
        Storage.remove("grape", 10);
        Map<String, Integer> inventory = Storage.getInventory();
        assertEquals(-5, inventory.get("grape"));
    }

    @Test
    void getInventory_emptyStorage_returnsEmptyMap() {
        Map<String, Integer> inventory = Storage.getInventory();
        assertTrue(inventory.isEmpty());
    }
}
