package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_TEST_AMOUNT = 80;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new StorageImpl();
    }

    @Test
    void getValue_NonExistingKey() {
        String key = APPLE_FRUIT;
        Integer retrievedValue = storage.getValue(key);

        assertNull(retrievedValue);
    }

    @Test
    void setAndGetValue() {
        String key = APPLE_FRUIT;
        Integer value = APPLE_TEST_AMOUNT;
        storage.setValue(key, value);

        assertEquals(value, storage.getValue(key));
    }

    @Test
    void getKeys() {
        String key1 = APPLE_FRUIT;
        storage.setValue(key1, APPLE_TEST_AMOUNT);

        Set<String> keys = storage.getKeys();

        assertTrue(keys.contains(key1));
    }

    @AfterEach
    void afterEach() {
        new StorageImpl().clear();
    }
}
