package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FruitShopTest {
    @Test
    void getQuantityExistingFruit() {
        FruitShop.storage.put("Apple", 10);
        int result = FruitShop.getQuantity("Apple");
        assertEquals(10, result);
    }

    @Test
    void getQuantityNonExistingFruit() {
        int result = FruitShop.getQuantity("Banana");
        assertEquals(0, result);
    }

    @AfterEach
    void tearDown() {
        FruitShop.storage.clear();
    }
}
