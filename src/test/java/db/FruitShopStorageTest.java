package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopStorageTest {
    private Map<String, Integer> fruitShop;

    @BeforeEach
    void setUp() {
        fruitShop = FruitShopStorage.fruitShop;
        fruitShop.clear();
    }

    @Test
    void addFruitShop_Ok() {
        fruitShop.put("banana", 121);
        assertEquals(121, fruitShop.get("banana"));
    }

    @Test
    void addFruitShop_NotOk() {
        fruitShop.put("banana", 121);
        assertNotEquals(122, fruitShop.get("banana"));
    }

    @Test
    void emptyFruitShop_Ok() {
        assertTrue(fruitShop.isEmpty());
    }
}
