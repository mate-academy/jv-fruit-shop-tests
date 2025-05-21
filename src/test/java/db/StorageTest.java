package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StorageTest {

    @AfterEach
    void cleanUp() {
        Storage.fruits.clear();
    }

    @Test
    void testUpdateFruitAmount_Ok() {
        Storage.add("apple", 1);
        assertEquals(1,Storage.fruits.get("apple"));
    }

    @Test
    void testUpdateFruitAmount_NotOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Storage.add("apple", -1));
        assertEquals("Fruits amount can't be negative", exception.getMessage());
    }

    @Test
    void testSetFruitAmount_Ok() {
        Storage.setAmount("orange", 20);
        assertEquals(20,Storage.fruits.get("orange"));
    }

    @Test
    void testGetFruitAmountFromEmptyStorage_Ok() {
        assertEquals(0,Storage.getAmount("apple"));
    }

    @Test
    void testGetFruitAmountFromStorage_Ok() {
        Storage.setAmount("apple", 40);
        assertEquals(40, Storage.fruits.get("apple"));
    }

    @Test
    void testRemoveFruitAmount_Ok() {
        Storage.add("apple", 10);
        Storage.remove("apple", 5);
        assertEquals(5, Storage.getAmount("apple"));
    }

    @Test
    void testClearStorage_Ok() {
        Storage.fruits.put("apple",100);
        Storage.clearStorage();
        assertEquals(0, Storage.fruits.size());
    }
}
