package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
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

        assertEquals(15, storage.getQuantity("apple"));
    }

    @Test
    public void removeFruit_decreasesQuantityCorrectly() {
        storage.addFruit("banana", 20);
        storage.removeFruit("banana", 5);

        assertEquals(15, storage.getQuantity("banana"));
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

        assertEquals(10, storage.getQuantity("orange"));
    }

    @Test
    public void getStorage_returnsUnmodifiableMap() {
        storage.addFruit("grape", 8);
        Map<String, Integer> storageMap = storage.getStorage();

        assertThrows(UnsupportedOperationException.class, () -> {
            storageMap.put("watermelon", 5);
        });
    }

    @Test
    public void setFruitBalance_setsQuantityCorrectly() {
        storage.setFruitBalance("peach", 12);

        assertEquals(12, storage.getQuantity("peach"));
    }

    @Test
    public void clear_removesAllFruits() {
        storage.addFruit("plum", 4);
        storage.clear();

        assertEquals(0, storage.getQuantity("plum"));
    }
}

