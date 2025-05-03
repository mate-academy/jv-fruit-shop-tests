package core.basesyntax.db;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    public void setUp() {
        Storage storage = new Storage();
    }

    @Test
    void testAddFruits_ok() {
        Storage.addFruits("apple", 12);
        Assertions.assertEquals(Storage.getFruitBalance("apple"), 12);

        Storage.addFruits("banana", 2);
        Assertions.assertEquals(Storage.getFruitBalance("banana"), 2);
    }

    @Test
    void testAddFruits_notOk() {
        Storage.addFruits("apple", 12);
        Assertions.assertNotEquals(Storage.getFruitBalance("apple"), 2);

        Storage.addFruits("banana", 2);
        Assertions.assertNotEquals(Storage.getFruitBalance("banana"), 3);
    }

    @Test
    void removeFruit_ok() {
        Storage.addFruits("apple", 10);
        Storage.addFruits("banana", 5);

        Storage.removeFruit("apple", 3);
        Storage.removeFruit("banana", 7);

        Assertions.assertEquals(7, Storage.getFruitBalance("apple"));
        Assertions.assertEquals(0, Storage.getFruitBalance("banana"));
    }

    @Test
    public void removeFruitWithZeroQuantity() {
        Storage.addFruits("apple", 10);

        Storage.removeFruit("apple", 0);

        Assertions.assertEquals(10, Storage.getFruitBalance("apple"));
    }

    @Test
    void removeFruit_notOk() {
        Storage.addFruits("apple", 12);
        Storage.removeFruit("apple", 12);

        Assertions.assertNotEquals(Storage.getFruitBalance("apple"), 3);
    }

    @Test
    void getFruitBalance_ok() {
        Storage.addFruits("apple", 10);
        Assertions.assertEquals(Storage.getFruitBalance("apple"), 10);
        Assertions.assertEquals(Storage.getFruitBalance("carrot"), 0);
    }

    @Test
    void getFruitBalance_notOk() {
        Assertions.assertNotEquals(Storage.getFruitBalance("carrot"), 1);
    }

    @Test
    void getAllFruitBalances() {
        Storage.addFruits("apple", 10);
        Storage.addFruits("banana", 5);

        Map<String, Integer> testMap = Storage.getAllFruitBalances();

        Assertions.assertEquals(testMap.get("apple"), Storage.getFruitBalance("apple"));
        Assertions.assertEquals(testMap.get("banana"), Storage.getFruitBalance("banana"));
    }
}
