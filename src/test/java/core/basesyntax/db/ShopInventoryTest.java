package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopInventoryTest {
    private static final String TEST_FRUIT = "banana";
    private static final int SMALLER_EDGE_FRUIT_QUANTITY_VALUE = 1;
    private static final int POSITIVE_FRUIT_QUANTITY_VALUE = 100;
    private static final int NEGATIVE_FRUIT_QUANTITY_VALUE = -100;
    private ShopInventory shopInventory;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        shopInventory = new ShopInventory();
        inventory = ShopInventory.inventory;
    }

    @AfterEach
    void tearDown() {
        inventory.clear();
    }

    @Test
    void setFruitQuantity_ValidQuantity_Ok() {
        int expectedQuantity = 1;
        shopInventory.setFruitQuantity(TEST_FRUIT, SMALLER_EDGE_FRUIT_QUANTITY_VALUE);
        assertEquals(expectedQuantity, inventory.get(TEST_FRUIT));
    }

    @Test
    void setFruitQuantity_NegativeQuantity_NotOk() {
        assertThrows(RuntimeException.class,
                () -> shopInventory.setFruitQuantity(TEST_FRUIT, NEGATIVE_FRUIT_QUANTITY_VALUE));
    }

    @Test
    void addFruitQuantity_NewFruit_Ok() {
        int expectedQuantity = 1;
        shopInventory.addFruitQuantity(TEST_FRUIT, SMALLER_EDGE_FRUIT_QUANTITY_VALUE);
        assertEquals(expectedQuantity, inventory.get(TEST_FRUIT));
    }

    @Test
    void addFruitQuantity_ExistingFruit_Ok() {
        int expectedQuantity = 101;
        inventory.put(TEST_FRUIT, SMALLER_EDGE_FRUIT_QUANTITY_VALUE);
        shopInventory.addFruitQuantity(TEST_FRUIT, POSITIVE_FRUIT_QUANTITY_VALUE);
        assertEquals(expectedQuantity, inventory.get(TEST_FRUIT));
    }

    @Test
    void addFruitQuantity_NegativeQuantity_NotOk() {
        assertThrows(RuntimeException.class,
                () -> shopInventory.addFruitQuantity(TEST_FRUIT, NEGATIVE_FRUIT_QUANTITY_VALUE));
    }

    @Test
    void deductFruitQuantity_ValidQuantity_Ok() {
        int deductQuantity = 99;
        int expectedQuantity = 1;
        inventory.put(TEST_FRUIT, POSITIVE_FRUIT_QUANTITY_VALUE);
        shopInventory.deductFruitQuantity(TEST_FRUIT, deductQuantity);
        assertEquals(expectedQuantity, inventory.get(TEST_FRUIT));
    }

    @Test
    void deductFruitQuantity_BiggerQuantityThanExist_NotOk() {
        int deductBiggerQuantityValue = 2;
        inventory.put(TEST_FRUIT, SMALLER_EDGE_FRUIT_QUANTITY_VALUE);
        assertThrows(RuntimeException.class,
                () -> shopInventory.deductFruitQuantity(TEST_FRUIT, deductBiggerQuantityValue));
    }

    @Test
    void deductFruitQuantity_NegativeQuantity_NotOk() {
        inventory.put(TEST_FRUIT, SMALLER_EDGE_FRUIT_QUANTITY_VALUE);
        assertThrows(RuntimeException.class,
                () -> shopInventory.deductFruitQuantity(TEST_FRUIT, NEGATIVE_FRUIT_QUANTITY_VALUE));
    }
}
