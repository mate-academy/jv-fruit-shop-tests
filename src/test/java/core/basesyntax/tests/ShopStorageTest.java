package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.db.ShopStorageImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopStorageTest {
    private ShopStorage shopStorage;

    @BeforeEach
    public void setUp() {
        shopStorage = new ShopStorageImpl();
    }

    @Test
    public void updateQuantity_existingFruit_Ok() {
        shopStorage.updateQuantity("banana", 15);
        shopStorage.updateQuantity("apple", 10);

        assertEquals(15, shopStorage.getQuantity("banana"));
        assertEquals(10, shopStorage.getQuantity("apple"));
    }

    @Test
    public void updateQuantity_nonExistingFruit_NotOk() {
        shopStorage.updateQuantity("apple", 10);

        assertEquals(0, shopStorage.getQuantity("banana"));
    }

    @Test
    public void getQuantity_existingFruit_Ok() {
        shopStorage.updateQuantity("apple", 10);
        assertEquals(10, shopStorage.getQuantity("apple"));
    }

    @Test
    public void getQuantity_nonExistingFruit_NotOk() {
        assertEquals(0, shopStorage.getQuantity("banana"));
    }

    @Test
    public void getFruitQuantities_nonEmptyStorage_Ok() {
        shopStorage.updateQuantity("banana", 10);
        shopStorage.updateQuantity("apple", 5);

        Map<String, Integer> fruitQuantities = shopStorage.getFruitQuantities();
        assertNotNull(fruitQuantities);
        assertEquals(2, fruitQuantities.size());
        assertEquals(10, fruitQuantities.get("banana"));
        assertEquals(5, fruitQuantities.get("apple"));
    }

    @Test
    public void getFruitQuantities_emptyStorage_Ok() {
        Map<String, Integer> fruitQuantities = shopStorage.getFruitQuantities();
        assertNotNull(fruitQuantities);
        assertTrue(fruitQuantities.isEmpty());
    }
}
