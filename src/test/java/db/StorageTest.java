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
    void testSetNegativeAmount_notOk() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Storage.setAmount("apple", -10));
        assertEquals("Fruits amount can't be negative", ex.getMessage());
    }

    @Test
    void testGetFruitAmount_ok() {
        Storage.add("apple", 10);
        int amount = Storage.getAmount("apple");
        assertEquals(10, amount, "Should return correct amount for existing fruit");
    }

    @Test
    void testGetFruitAmount_notOk() {
        int amount = Storage.getAmount("banana");
        assertEquals(0, amount, "Should return 0 for fruit not in storage");
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
        Storage.fruits.put("apple", 10);
        Storage.remove("apple", 5);
        assertEquals(5, Storage.getAmount("apple"));
    }

    @Test
    void testRemoveDefunctFruit_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Storage.remove("banana", 5));
        assertEquals("Not enough banana in storage.", exception.getMessage());
    }

    @Test
    void testRemoveFruitWithNotEnoughAmount_notOk() {
        Storage.fruits.put("apple", 3);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Storage.remove("apple", 5));
        assertEquals("Not enough apple in storage.", exception.getMessage());
    }

    @Test
    void testClearStorage_Ok() {
        Storage.fruits.put("apple",100);
        Storage.clearStorage();
        assertEquals(0, Storage.fruits.size());
    }
}
