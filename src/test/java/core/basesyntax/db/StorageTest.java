package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void addFruit_newFruit() {
        storage.addFruit("apple", 10);

        assertEquals(10, storage.getQuantity("apple"));
    }

    @Test
    void addFruit_existingFruit() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", 5);

        assertEquals(15, storage.getQuantity("apple"));
    }

    @Test
    void removeFruit_validQuantity() {
        storage.addFruit("banana", 5);

        storage.removeFruit("banana", 3);

        assertEquals(2, storage.getQuantity("banana"));
    }

    @Test
    void removeFruit_moreThanAvailable() {
        storage.addFruit("orange", 2);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> storage.removeFruit("orange", 3)
        );

        assertEquals(
                "Not enough fruit in storage",
                exception.getMessage()
        );
    }

    @Test
    void removeFruit_negativeQuantity() {
        storage.addFruit("grape", 5);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> storage.removeFruit("grape", -1)
        );

        assertEquals(
                "Quantity must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void removeFruit_zeroQuantity() {
        storage.addFruit("grape", 5);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> storage.removeFruit("grape", 0)
        );

        assertEquals(
                "Quantity must be greater than 0",
                exception.getMessage()
        );
    }

    @Test
    void getQuantity_nonExistingFruit() {
        assertEquals(0, storage.getQuantity("kiwi"));
    }

    @Test
    void setFruitQuantity_validQuantity() {
        storage.setFruitQuantity("mango", 15);

        assertEquals(15, storage.getQuantity("mango"));
    }

    @Test
    void setFruitQuantity_negativeQuantity() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> storage.setFruitQuantity("peach", -1)
        );

        assertEquals(
                "Quantity must be greater than or equal to 0",
                exception.getMessage()
        );
    }

    @Test
    void getStorage_returnsCopy() {
        storage.addFruit("apple", 10);

        Map<String, Integer> result = storage.getStorage();

        assertEquals(10, result.get("apple"));
    }
}
