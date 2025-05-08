package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryServiceTest {
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        inventoryService = new InventoryService();
    }

    @Test
    void addFruit_shouldAddQuantityToEmptyStorage() {
        inventoryService.addFruit("apple", 10);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void addFruit_shouldAddQuantityToExistingFruit() {
        Storage.inventory.put("banana", 5);
        inventoryService.addFruit("banana", 3);
        assertEquals(8, Storage.inventory.get("banana"));
    }

    @Test
    void getQuantity_shouldReturnCorrectQuantity() {
        Storage.inventory.put("orange", 7);
        assertEquals(7, inventoryService.getQuantity("orange"));
    }

    @Test
    void getQuantity_shouldReturnZeroForAbsentFruit() {
        assertEquals(0, inventoryService.getQuantity("kiwi"));
    }

    @Test
    void removeFruit_shouldReduceQuantityCorrectly() {
        Storage.inventory.put("grape", 10);
        inventoryService.removeFruit("grape", 4);
        assertEquals(6, Storage.inventory.get("grape"));
    }

    @Test
    void removeFruit_shouldThrowExceptionIfNotEnough() {
        Storage.inventory.put("mango", 2);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.removeFruit("mango", 5)
        );
        assertEquals("Not enough fruit to remove", exception.getMessage());
    }

    @Test
    void removeFruit_shouldThrowExceptionIfFruitAbsent() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventoryService.removeFruit("pear", 1)
        );
        assertEquals("Not enough fruit to remove", exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.inventory.clear();
    }
}
