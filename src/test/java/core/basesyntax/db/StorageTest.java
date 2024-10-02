package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void addFruit_increasesQuantityCorrectly() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", 5);

        Map<String, Integer> internalStorage = storage.getStorage();
        assertEquals(1, internalStorage.size());
        assertEquals(15, internalStorage.get("apple"));
    }

    @Test
    public void removeFruit_decreasesQuantityCorrectly() {
        storage.addFruit("banana", 20);
        storage.removeFruit("banana", 5);

        Map<String, Integer> internalStorage = storage.getStorage();
        assertEquals(1, internalStorage.size());
        assertEquals(15, internalStorage.get("banana"));
    }

    @Test
    public void removeFruit_insufficientStock_throwsRuntimeException() {
        storage.addFruit("kiwi", 3);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            storage.removeFruit("kiwi", 5);
        });

        String expectedMessage = "Insufficient stock for kiwi";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getQuantity_nonExistentFruit_returnsZero() {
        assertEquals(0, storage.getQuantity("non-existent-fruit"));
    }

    @Test
    public void updateFruit_setsQuantityCorrectly() {
        storage.addFruit("orange", 5);
        storage.updateFruit("orange", 10);

        Map<String, Integer> internalStorage = storage.getStorage();
        assertEquals(1, internalStorage.size());
        assertEquals(10, internalStorage.get("orange"));
    }

    @Test
    public void setFruitBalance_setsQuantityCorrectly() {
        storage.setFruitBalance("peach", 12);

        Map<String, Integer> internalStorage = storage.getStorage();
        assertEquals(1, internalStorage.size());
        assertEquals(12, internalStorage.get("peach"));
    }

    @Test
    public void clear_removesAllFruits() {
        storage.addFruit("plum", 4);
        storage.clear();

        Map<String, Integer> internalStorage = storage.getStorage();
        assertTrue(internalStorage.isEmpty());
    }
}
