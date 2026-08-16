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
    void addFruit_newFruit_createsPosition() {
        storage.addFruit("apple", 10);

        assertEquals(10, storage.getQuantity("apple"));
    }

    @Test
    void addFruit_existingFruit_increasesQuantity() {
        storage.addFruit("apple", 10);
        storage.addFruit("apple", 5);

        assertEquals(15, storage.getQuantity("apple"));
    }

    @Test
    void removeFruit_validQuantity_decreasesQuantity() {
        storage.addFruit("banana", 5);

        storage.removeFruit("banana", 3);

        assertEquals(2, storage.getQuantity("banana"));
    }

    @Test
    void removeFruit_moreThanAvailable_throwsException() {
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
    void removeFruit_negativeQuantity_throwsException() {
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
    void removeFruit_zeroQuantity_throwsException() {
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
    void getQuantity_nonExistingFruit_createsPositionWithZeroQuantity() {
        assertEquals(0, storage.getQuantity("kiwi"));
    }

    @Test
    void setFruitQuantity_validQuantity_isOk() {
        storage.setFruitQuantity("mango", 15);

        assertEquals(15, storage.getQuantity("mango"));
    }

    @Test
    void setFruitQuantity_ZeroQuantity_isOk() {
        storage.setFruitQuantity("mango", 0);

        assertEquals(0, storage.getQuantity("mango"));
    }

    @Test
    void setFruitQuantity_negativeQuantity_throwsException() {
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
    void getStorage_returnsCopy_isOk() {
        storage.addFruit("apple", 10);

        Map<String, Integer> result = storage.getStorage();

        assertEquals(10, result.get("apple"));
    }
}
