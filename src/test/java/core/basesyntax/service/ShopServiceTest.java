package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import core.basesyntax.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        shopService = new ShopServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    void addFruits_addNewFruit_Ok() {
        FruitInStorage actualFruitInStorage = shopService.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void addFruits_addExistingFruit_Ok() {
        Storage.FRUITS.put("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);

        expectedFruitInStorage.setAmount(20);
        FruitInStorage actualFruitInStorage = shopService.addFruits("banana", 8);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void addFruits_addTwoFruit_Ok() {
        shopService.addFruits("banana", 12);
        shopService.addFruits("apple", 12);
        assertEquals(2, Storage.FRUITS.size());
    }

    @Test
    void removeFruits_removeExistingFruit_Ok() {
        FruitInStorage actualFruitInStorage = shopService.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);

        expectedFruitInStorage.setAmount(9);
        actualFruitInStorage = shopService.removeFruits("banana", 3);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);
    }

    @Test
    void removeFruits_removeNonExistingFruit_NotOk() {
        assertNull(shopService.removeFruits("banana", 3));
    }

    @Test
    void removeFruits_removeBigAmountFruit_NotOk() {
        FruitInStorage actualFruitInStorage = shopService.addFruits("banana", 12);
        FruitInStorage expectedFruitInStorage = new FruitInStorage("banana", 12);
        assertEquals(expectedFruitInStorage, actualFruitInStorage);

        assertThrows(RuntimeException.class, () -> shopService.removeFruits("banana", 13));
    }
}
