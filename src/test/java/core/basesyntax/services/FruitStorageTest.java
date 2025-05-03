package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private static final String APPLE_TEST = "apple";
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    void purchaseItem_notEnoughFruitsInStorage_NotOk() {
        fruitStorage.saveItem(APPLE_TEST, 5);
        assertThrows(IllegalArgumentException.class,
                () -> fruitStorage.purchaseItem(APPLE_TEST, 10),
                "Not enough fruit: 5");
    }

    @Test
    void getAmount_UnknownFruit_Ok() {
        int expected = 0;
        int actual = fruitStorage.getAmount(APPLE_TEST);
        assertEquals(expected, actual);
    }

    @Test
    void clear_Ok() {
        fruitStorage.saveItem(APPLE_TEST, 2);
        fruitStorage.clear();
        int expected = 0;
        int actual = fruitStorage.getAmount(APPLE_TEST);
        assertEquals(expected, actual);
    }

    @AfterEach
    void afterVoid() {
        fruitStorage.clear();
    }
}
