package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 10;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void fruits_EmptyStorage_Success() {
        assertEquals(new HashMap<String, Integer>(), Storage.fruits);
    }

    @Test
    void fruits_AddFruitToStorage_Success() {
        Storage.fruits.put(FRUIT_NAME, FRUIT_QUANTITY);
        Map<String, Integer> expectedFruits = new HashMap<>();
        expectedFruits.put(FRUIT_NAME, FRUIT_QUANTITY);

        assertEquals(expectedFruits, Storage.fruits);
    }

    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }
}
