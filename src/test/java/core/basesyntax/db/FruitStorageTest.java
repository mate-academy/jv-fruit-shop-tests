package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitStorageTest {
    private final FruitStorage storage = new FruitStorageImpl();
    private final String fruit = "banana";
    private final Integer quantity = 100;

    @Test
    void add_Ok() {
        storage.add(fruit, quantity);
        Integer expected = storage.getQuantity(fruit);
        Assertions.assertEquals(expected, quantity);
    }

    @Test
    void getAll_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        storage.add(fruit, quantity);
        expected.put(fruit, quantity);
        Map<String, Integer> fruitMap = storage.getAll();
        Assertions.assertEquals(expected, fruitMap);
    }

    @Test
    void add_NullKey_NotOk() {
        int quantity = 90;
        try {
            storage.add(null, quantity);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException occurred because the value is empty");
    }

    @Test
    void add_NullValue_NotOk() {
        String fruitName = "apple";
        try {
            storage.add(fruitName, null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException occurred because the value is empty");
    }

    @Test
    void getQuantity_nullQuantity_NotOk() {
        try {
            storage.getQuantity(null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException occurred because the value is empty");
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }
}
