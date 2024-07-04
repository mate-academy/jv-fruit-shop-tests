package core.basesyntax;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void addFruit_positiveQuantity() {
        Storage.addFruit("apple", 10);
        assertEquals(10, Storage.getFruitQuantity("apple"));

        // Adding more fruits to test the accumulation
        Storage.addFruit("apple", 25);
        assertEquals(35, Storage.getFruitQuantity("apple"));
    }

    @Test
    void addFruit_existingFruit() {
        Storage.addFruit("apple", 10);
        Storage.addFruit("apple", 5);
        assertEquals(15, Storage.getFruitQuantity("apple"));
    }

    @Test
    void getFruitStorage_notEmpty() {
        Storage.addFruit("apple", 10);
        Storage.addFruit("banana", 20);
        Map<String, Integer> fruitStorage = Storage.getFruitStorage();
        assertEquals(2, fruitStorage.size());
        assertEquals(10, fruitStorage.get("apple"));
        assertEquals(20, fruitStorage.get("banana"));
    }

    @Test
    void addFruit_addNewFruit_ok() {
        Storage.addFruit("orange", 10);
        assertEquals(10, Storage.getFruitQuantity("orange"));
    }

    @Test
    void getFruitQuantity_nonExistingFruit_ok() {
        assertEquals(0, Storage.getFruitQuantity("kiwi"));
    }
}

