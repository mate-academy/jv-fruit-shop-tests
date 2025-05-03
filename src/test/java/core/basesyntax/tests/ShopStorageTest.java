package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.db.ShopStorageImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopStorageTest {
    private static ShopStorage shopStorage;

    @BeforeAll
    public static void setUp() {
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
    public void getQuantity_existingFruit_Ok() {
        shopStorage.updateQuantity("apple", 10);
        assertEquals(10, shopStorage.getQuantity("apple"));
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
}
