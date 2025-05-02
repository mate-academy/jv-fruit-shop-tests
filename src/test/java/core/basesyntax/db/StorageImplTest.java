package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void getValue_NonExistingKey_NotOk() {
        String key = APPLE_FRUIT;
        Integer retrievedValue = storage.getValue(key);

        assertNull(retrievedValue);
    }

    @Test
    void setAndGetValue_ValidData_Ok() {
        String key = APPLE_FRUIT;
        Integer value = APPLE_TEST_AMOUNT;
        storage.setValue(key, value);

        assertEquals(value, storage.getValue(key));
    }

    @AfterEach
    void afterEach() {
        storage.clear();
    }
}
