package core.basesyntax.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    public void SetUp() {
        inventory = new Inventory();
    }

    @Test
    public void getFruitQuantityReturnsZero() {
        inventory.setFruitQuantity("apple", null);
        assertEquals(0, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void getNonExistentFruitQuantityReturnsZero() {
        assertEquals(0, inventory.getFruitQuantity("banana"));
    }

    @Test
    public void getFruitQuantityReturnsQuantity() {
        inventory.setFruitQuantity("apple", 10);
        assertEquals(10, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void getAllFruitsReturnsCopy() {
        inventory.setFruitQuantity("apple", 10);
        Map<String, Integer> fruitMap = inventory.getAllFruits();
        fruitMap.put("apple", 999);
        assertEquals(10, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void twiceSetFruitQuantityOverwritesValue() {
        inventory.setFruitQuantity("apple", 10);
        inventory.setFruitQuantity("apple", 7);
        assertEquals(7, inventory.getFruitQuantity("apple"));
    }
}
