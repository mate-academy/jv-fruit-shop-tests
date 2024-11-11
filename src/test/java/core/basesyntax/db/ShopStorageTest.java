package core.basesyntax.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ShopStorageTest {

    private ShopStorage shopStorage;

    @BeforeEach
    public void setUp() {
        shopStorage = ShopStorage.getInstance();
        shopStorage.setFruitQuantity("banana", 100);
    }

    @Test
    public void setFruitQuantity_validData_success() {
        shopStorage.setFruitQuantity("apple", 50);
        assertEquals(50, shopStorage.getFruitQuantity("apple"));
    }

    @Test
    public void updateFruitQuantity_validData_success() {
        shopStorage.updateFruitQuantity("banana", 50);
        assertEquals(150, shopStorage.getFruitQuantity("banana"));
    }

    @Test
    public void getFruitQuantity_fruitNotFound_zero() {
        assertEquals(0, shopStorage.getFruitQuantity("orange"));
    }
}
