package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void getFruitStorage_getInstance_Ok() {
        Map<String, Integer> storage = Storage.getFruitStorage();
        assertNotNull(storage);
        assertSame(storage, Storage.getFruitStorage());
    }

    @Test
    void getFruitStorage_isHashMap_Ok() {
        assertInstanceOf(HashMap.class, Storage.getFruitStorage(),
                "Expected instance of HashMap");
    }

    @Test
    void storage_acceptsStringAndInteger_Ok() {
        String fruit = "Grapefruit";
        Integer quantity = 500;

        Storage.getFruitStorage().put(fruit, quantity);

        Storage.getFruitStorage().forEach((key, value) -> {
            assertInstanceOf(String.class, key,
                    "Key should be of type String");
            assertInstanceOf(Integer.class, value,
                    "Value should be of type Integer");
        });
    }
}
