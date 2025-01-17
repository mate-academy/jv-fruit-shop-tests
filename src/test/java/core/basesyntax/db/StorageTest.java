package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void modifyFruitStorage_addPositiveQuantity_ok() {
        Storage.modifyFruitStorage("apple", 50);
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(50, actual, "Counts of apples need be 50");
    }

    @Test
    void modifyFruitStorage_addZeroQuantity_ok() {
        Storage.modifyFruitStorage("apple", 0);
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(0, actual, "Counts of apples should be 0");
    }

    @Test
    void modifyFruitStorage_addNegativeQuantity_ok() {
        Storage.modifyFruitStorage("apple", 50);
        Storage.modifyFruitStorage("apple", -20);
        int actual = Storage.getFruitQuantity("apple");
        assertEquals(30, actual, "Counts of apples should be 30");
    }

    @Test
    void modifyFruitStorage_addNegativeQuantity_throwsException() {
        Storage.modifyFruitStorage("apple", 10);
        Exception exception = assertThrows(IllegalStateException.class,
                () -> Storage.modifyFruitStorage("apple", -20));
        String expectedMessage = "Stock for fruit apple cannot be negative.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getFruitQuantity_nonExistingFruit_returnsZero() {
        int actual = Storage.getFruitQuantity("banana");
        assertEquals(0, actual, "The amount of dissolving fruit should be 0");
    }

    @Test
    void getAllFruits_returnsAllEntries_ok() {
        Storage.modifyFruitStorage("apple", 30);
        Storage.modifyFruitStorage("banana", 20);
        Map<String, Integer> fruits = Storage.getAllFruits();

        assertEquals(2, fruits.size(), "Storage size need be 2");
        assertEquals(30, fruits.get("apple"), "Counts of apples should be 30");
        assertEquals(20, fruits.get("banana"), "Counts of apples should be 20");
    }
}
