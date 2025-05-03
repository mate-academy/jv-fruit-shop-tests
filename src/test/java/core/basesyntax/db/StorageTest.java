package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final int STARTING_QUANTITY = 0;

    @BeforeEach
    public void setUp() {
        Storage.setFruitQuantity("apple", STARTING_QUANTITY);
        Storage.setFruitQuantity("banana", STARTING_QUANTITY);
    }

    @Test
    public void test_getFruitQuantity_existingFruit() {
        Integer quantity = Storage.getFruitQuantity("apple");
        assertEquals(0, quantity, "Quantity of apples should be 0");
    }

    @Test
    public void test_getFruitQuantity_nonExistingFruit() {
        Integer quantity = Storage.getFruitQuantity("orange");
        assertEquals(0, quantity, "Quantity of non-existing fruit should be 0");
    }

    @Test
    public void test_setFruitQuantity() {
        Storage.setFruitQuantity("banana", 20);
        Integer quantity = Storage.getFruitQuantity("banana");
        assertEquals(20, quantity, "Quantity of bananas should be 20");
    }

    @Test
    public void test_getAllFruits() {
        Storage.setFruitQuantity("apple", 10);
        Storage.setFruitQuantity("banana", 20);

        Map<String, Integer> allFruits = Storage.getAllFruits();

        assertNotNull(allFruits, "All fruits map should not be null");
        assertEquals(2, allFruits.size(), "Size of all fruits map should be 2");
        assertEquals(10, allFruits.get("apple"), "Quantity of apples should be 10");
        assertEquals(20, allFruits.get("banana"), "Quantity of bananas should be 20");
    }

    @Test
    public void test_getAllFruits_emptyStorage() {
        Storage.clear();
        Map<String, Integer> allFruits = Storage.getAllFruits();
        assertTrue(allFruits.isEmpty(), "All fruits map should be empty");
    }
}
