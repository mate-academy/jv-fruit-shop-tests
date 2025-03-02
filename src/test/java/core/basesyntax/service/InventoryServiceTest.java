package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryServiceTest {

    private InventoryService inventoryService;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        inventory = new HashMap<>();
        inventoryService = new InventoryService(inventory);
    }

    @Test
    void addFruit_ShouldAddFruitToInventory() {
        inventoryService.addFruit("apple", 10);
        assertEquals(10, inventoryService.getQuantity("apple"));
    }

    @Test
    void addFruit_ShouldIncreaseExistingQuantity() {
        inventoryService.addFruit("apple", 10);
        inventoryService.addFruit("apple", 5);
        assertEquals(15, inventoryService.getQuantity("apple"));
    }

    @Test
    void removeFruit_ShouldReduceQuantity() {
        inventoryService.addFruit("apple", 10);
        inventoryService.removeFruit("apple", 5);
        assertEquals(5, inventoryService.getQuantity("apple"));
    }

    @Test
    void removeFruit_ShouldThrowException_WhenNotEnoughQuantity() {
        inventoryService.addFruit("apple", 5);
        assertThrows(IllegalArgumentException.class,
                () -> inventoryService.removeFruit("apple", 10));
    }

    @Test
    void getQuantity_ShouldReturnZero_WhenFruitNotInInventory() {
        assertEquals(0, inventoryService.getQuantity("banana"));
    }

    @Test
    void getInventory_ShouldReturnUnmodifiableMap() {
        Map<String, Integer> unmodifiableInventory = inventoryService.getInventory();
        assertThrows(UnsupportedOperationException.class,
                () -> unmodifiableInventory.put("orange", 5));
    }
}
