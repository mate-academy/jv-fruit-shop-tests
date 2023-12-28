package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    void add_fruit_ok() {
        Map<String, Integer> fruitInventory = new HashMap<>();
        fruitInventory.put("apple", 20);

        fruitStorage.addFruit("apple", 20);
        assertEquals(fruitInventory, fruitStorage.getFruitInventory());
    }

    @Test
    void substract_fruit_notOk() {
        fruitStorage.addFruit("banana", 5);

        assertThrows(RuntimeException.class, () -> fruitStorage.substractFruit("banana", 10));
    }

    @Test
    void substract_fruit_ok() {
        fruitStorage.addFruit("apple", 20);

        fruitStorage.substractFruit("apple", 10);

        int remainingQuantity = fruitStorage.getFruitInventory().getOrDefault("apple", 0);
        assertEquals(10, remainingQuantity);
    }

    @Test
    void get_fruitInventory_ok() {
        fruitStorage.addFruit("apple", 35);

        Map<String, Integer> fruitInventory = fruitStorage.getFruitInventory();

        assertEquals(35, (int) fruitInventory.getOrDefault("apple", 0));
    }
}
