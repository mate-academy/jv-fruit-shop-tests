import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryServiceTest {

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
    }

    @Test
    void addFruit_ShouldHandleZeroQuantity() {
        inventoryService.addFruit("apple", 0);
        assertEquals(0, inventoryService.getQuantity("apple"));
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
    void addFruit_ShouldHandleMultipleFruits() {
        inventoryService.addFruit("apple", 5);
        inventoryService.addFruit("banana", 8);
        inventoryService.addFruit("orange", 3);
        assertEquals(5, inventoryService.getQuantity("apple"));
        assertEquals(8, inventoryService.getQuantity("banana"));
        assertEquals(3, inventoryService.getQuantity("orange"));
    }

    @Test
    void removeFruit_NegativeQuantity_ShouldIncreaseInventory() {
        inventoryService.addFruit("apple", 10);
        inventoryService.removeFruit("apple", -5);
        assertEquals(15, inventoryService.getQuantity("apple"));
    }
}
