package core.basesyntax.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDBtest {

    private FruitDB fruitDB;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
    }

    @Test
    void add_validData_updatesInventory() {
        fruitDB.add("apple", 10);
        fruitDB.add("banana", 20);
        Map<String, Integer> inventory = fruitDB.getInventory();
        assertEquals(2, inventory.size());
        assertEquals(10, inventory.get("apple"));
        assertEquals(20, inventory.get("banana"));
        fruitDB.add("apple", 5);
        assertEquals(15, inventory.get("apple"));
    }

    @Test
    void subtract_validData_updatesInventory() {
        fruitDB.add("orange", 15);
        fruitDB.add("pear", 10);
        fruitDB.subtract("orange", 5);
        Map<String, Integer> inventory = fruitDB.getInventory();
        assertEquals(10, inventory.get("orange"));
    }

    @Test
    void subtract_insufficientInventory_throwsException() {
        fruitDB.add("grape", 5);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> fruitDB.subtract("grape", 10)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void subtract_fruitNotInInventory_throwsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> fruitDB.subtract("mango", 5)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void getInventory_noData_returnsEmptyMap() {
        Map<String, Integer> inventory = fruitDB.getInventory();
        assertTrue(inventory.isEmpty());
    }

    @Test
    void add_zeroOrNegativeQuantity_doesNotThrowException() {
        fruitDB.add("peach", 0);
        Map<String, Integer> inventory = fruitDB.getInventory();
        assertEquals(1, inventory.size());
        assertEquals(0, inventory.get("peach"));
        fruitDB.add("plum", -5);
        assertEquals(-5, inventory.get("plum"));
    }

    @Test
    void subtract_exactInventoryQuantity_updatesInventory() {
        fruitDB.add("cherry", 5);
        fruitDB.subtract("cherry", 5);
        Map<String, Integer> inventory = fruitDB.getInventory();
        assertEquals(0, inventory.get("cherry"));
    }
}
