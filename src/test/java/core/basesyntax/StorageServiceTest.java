package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.StorageService;

public class StorageServiceTest {
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Test
    void testAddFruit() {
        storageService.addFruit("apple", 10);
        assertEquals(10, storageService.getFruitQuantity("apple"));
    }

    @Test
    void testGetFruitQuantity() {
        storageService.addFruit("banana", 20);
        assertEquals(20, storageService.getFruitQuantity("banana"));
    }

    @Test
    void testGetFruitQuantities() {
        storageService.addFruit("apple", 10);
        storageService.addFruit("banana", 20);
        Map<String, Integer> fruitQuantities = storageService.getFruitQuantities();
        assertTrue(fruitQuantities.containsKey("apple"));
        assertTrue(fruitQuantities.containsKey("banana"));
        assertEquals(10, fruitQuantities.get("apple"));
        assertEquals(20, fruitQuantities.get("banana"));
    }

    @Test
    void testClearStorage() {
        storageService.addFruit("apple", 10);
        storageService.clearStorage();
        assertEquals(0, storageService.getFruitQuantity("apple"));
    }
}
