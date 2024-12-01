package core.basesyntax.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDBtest {

    @BeforeEach
    void setUp() {
        FruitDB.getInstance().getInventory().clear();
    }

    @Test
    void add_validData_updatesInventory() {
        FruitDB.getInstance().add("apple", 10);
        FruitDB.getInstance().add("banana", 20);
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        assertEquals(2, inventory.size());
        assertEquals(10, inventory.get("apple"));
        assertEquals(20, inventory.get("banana"));
        FruitDB.getInstance().add("apple", 5);
        assertEquals(15, inventory.get("apple"));
    }

    @Test
    void subtract_validData_updatesInventory() {
        FruitDB.getInstance().add("orange", 15);
        FruitDB.getInstance().add("pear", 10);
        FruitDB.getInstance().subtract("orange", 5);
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        assertEquals(10, inventory.get("orange"));
    }

    @Test
    void subtract_insufficientInventory_throwsException() {
        FruitDB.getInstance().add("grape", 5);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitDB.getInstance().subtract("grape", 10)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void subtract_fruitNotInInventory_throwsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitDB.getInstance().subtract("mango", 5)
        );
        assertEquals("Not enough inventory to subtract", exception.getMessage());
    }

    @Test
    void getInventory_noData_returnsEmptyMap() {
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        assertTrue(inventory.isEmpty());
    }

    @Test
    void add_zeroOrNegativeQuantity_updatesInventory() {
        FruitDB.getInstance().add("peach", 0);
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        assertEquals(1, inventory.size());
        assertEquals(0, inventory.get("peach"));
        FruitDB.getInstance().add("plum", -5);
        assertEquals(-5, inventory.get("plum"));
    }

    @Test
    void subtract_exactInventoryQuantity_updatesInventory() {
        FruitDB.getInstance().add("cherry", 5);
        FruitDB.getInstance().subtract("cherry", 5);
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        assertEquals(0, inventory.get("cherry"));
    }
}
