package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void getFruitQuantity_wrongFruitName_returnsZero() {
        Storage.add("apple", 20);
        assertEquals(0, Storage.getFruitQuantity("banana"));
    }

    @Test
    void decreaseQuantity_FruitReachesZero_RemovesFromStorage() {
        String fruit = "banana";
        int quantity = 5;
        Storage.add(fruit, quantity);
        Storage.remove(fruit, quantity);
        assertEquals(0, Storage.getFruitQuantity(fruit));
        assertFalse(Storage.fruits.containsKey(fruit));
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }
}
