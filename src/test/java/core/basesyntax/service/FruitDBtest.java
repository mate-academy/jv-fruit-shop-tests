package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDBtest {

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear(); // Clear the inventory before each test
    }

    @Test
    void add_validData_updatesInventory() {
        FruitDB.add("apple", 10);
        FruitDB.add("banana", 20);
        Map<String, Integer> inventory = FruitDB.getInventory();
        assertEquals(2, inventory.size());
        assertEquals(10, inventory.get("apple"));
        assertEquals(20, inventory.get("banana"));
    }

    @Test
    void subtract_validData_updatesInventory() {
        FruitDB.add("orange", 15);
        FruitDB.subtract("orange", 5);
        Map<String, Integer> inventory = FruitDB.getInventory();
        assertEquals(10, inventory.get("orange"));
    }

    @Test
    void subtract_insufficientInventory_throwsException() {
        FruitDB.add("grape", 5);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitDB.subtract("grape", 10)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void subtract_fruitNotInInventory_throwsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitDB.subtract("mango", 5)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void getInventory_noData_returnsEmptyMap() {
        Map<String, Integer> inventory = FruitDB.getInventory();
        assertTrue(inventory.isEmpty());
    }

    @Test
    void add_zeroOrNegativeQuantity_updatesInventory() {
        FruitDB.add("peach", 0);
        Map<String, Integer> inventory = FruitDB.getInventory();
        assertEquals(1, inventory.size());
        assertEquals(0, inventory.get("peach"));
        FruitDB.add("plum", -5);
        assertEquals(-5, inventory.get("plum"));
    }

    @Test
    void subtract_exactInventoryQuantity_updatesInventory() {
        FruitDB.add("cherry", 5);
        FruitDB.subtract("cherry", 5);
        Map<String, Integer> inventory = FruitDB.getInventory();
        assertEquals(0, inventory.get("cherry"));
    }
}
