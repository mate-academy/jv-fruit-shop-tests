package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import core.basesyntax.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private static final ShopService SHOP_SERVICE = new ShopServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.reset();
    }

    @Test
    void addFruits_addNewFruit_Ok() {
        FruitInStorage actualFruitInStorage = SHOP_SERVICE.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void addFruits_addExistingFruit_Ok() {
        FruitInStorage actualFruitInStorage = SHOP_SERVICE.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);

        expectedFruitInStorage.setAmount(20);
        actualFruitInStorage = SHOP_SERVICE.addFruits("banana", 8);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void addFruits_addTwoFruit_Ok() {
        SHOP_SERVICE.addFruits("banana", 12);
        SHOP_SERVICE.addFruits("apple", 12);
        assertEquals(2, Storage.fruits().size());
    }

    @Test
    void removeFruits_removeExistingFruit_Ok() {
        FruitInStorage actualFruitInStorage = SHOP_SERVICE.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);

        expectedFruitInStorage.setAmount(9);
        actualFruitInStorage = SHOP_SERVICE.removeFruits("banana", 3);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void removeFruits_removeNonExistingFruit_NotOk() {
        assertNull(SHOP_SERVICE.removeFruits("banana", 3));
    }

    @Test
    void removeFruits_removeBigAmountFruit_NotOk() {
        FruitInStorage actualFruitInStorage = SHOP_SERVICE.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);

        assertThrows(RuntimeException.class, () -> SHOP_SERVICE.removeFruits("banana", 13));
    }
}
