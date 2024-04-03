package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    public void clearStorage() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void checkAdedToStorage() {
        Storage.of("banana", 100);
        assertFalse(Storage.getFruitStorage().isEmpty());
    }

    @Test
    public void checkGettingQuantity() {
        int correctQuantity = 100;
        Storage.of("banana", 100);
        assertEquals(correctQuantity, Storage.getQuantity("banana"));
    }

}
