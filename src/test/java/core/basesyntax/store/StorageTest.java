package core.basesyntax.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void modifyFruitStorage_shouldIncreaseQuantity() {
        assertEquals(0, Storage.getFruitQuantity("apple"));

        Storage.modifyFruitStorage("apple", 10);

        assertEquals(10, Storage.getFruitQuantity("apple"));
    }

    @Test
    void modifyFruitStorage_shouldDecreaseQuantity() {
        Storage.modifyFruitStorage("apple", 10);

        Storage.modifyFruitStorage("apple", -5);

        assertEquals(5, Storage.getFruitQuantity("apple"));
    }

    @Test
    void modifyFruitStorage_shouldNotAllowNegativeQuantity() {
        Storage.modifyFruitStorage("apple", 10);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Storage.modifyFruitStorage("apple", -20);
        });

        assertEquals("Stock for fruit apple cannot be negative.", exception.getMessage());
    }

    @Test
    void getAllFruits_shouldReturnAllFruits() {
        Storage.modifyFruitStorage("apple", 10);
        Storage.modifyFruitStorage("banana", 20);

        Map<String, Integer> allFruits = Storage.getAllFruits();
        assertEquals(2, allFruits.size());
        assertEquals(10, allFruits.get("apple"));
        assertEquals(20, allFruits.get("banana"));
    }

    @Test
    void getFruitQuantity_shouldReturnZeroForNonExistentFruit() {
        assertEquals(0, Storage.getFruitQuantity("nonExistentFruit"));
    }
}
