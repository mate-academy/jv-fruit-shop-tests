package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopStorageTests {
    private FruitShopStorage fruitShopStorageDefault;

    @BeforeEach
    void setUp() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
    }

    @Test
    void fruitShopStorage_createStorage_ok() {
        FruitShopStorage fruitShopStorage = new FruitShopStorage();
        Assertions.assertNotNull(fruitShopStorage);
    }

    @Test
    void fruitShopStorage_getQuantity_ok() {
        int actual = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(100, actual);
    }

    @Test
    void fruitShopStorage_getQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitShopStorageDefault.getQuantity("lemon");
        });
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
