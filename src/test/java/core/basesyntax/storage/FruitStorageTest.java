package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    void purchaseItem_notEnoughFruitsInStorage_NotOk() {
        fruitStorage.saveItem("apple", 5);
        fruitStorage.purchaseItem("apple", 10);
        int expected = 0;
        int actual = fruitStorage.getAmount("apple");
        assertEquals(expected, actual);
    }

    @Test
    void getAmount_UnknownFruit_Ok() {
        int expected = 0;
        int actual = fruitStorage.getAmount("apple");
        assertEquals(expected, actual);
    }

    @Test
    void clear_Ok() {
        fruitStorage.saveItem("apple", 2);
        fruitStorage.clear();
        int expected = 0;
        int actual = fruitStorage.getAmount("apple");
        assertEquals(expected, actual);
    }

    @AfterEach
    void afterVoid() {
        fruitStorage.clear();
    }
}
