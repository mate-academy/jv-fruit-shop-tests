package core.basesyntax.db;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String DEFAULT_KEY1 = "Key1";
    private static final String DEFAULT_KEY2 = "Key2";
    private static final String DEFAULT_KEY3 = "Key3";
    private static final int DEFAULT_VALUE1 = 1;
    private static final int DEFAULT_VALUE2 = 2;
    private static final int DEFAULT_VALUE3 = 3;
    private static Map<String, Integer> storageTest;

    @BeforeEach
    void setUp() {
        storage.clear();
        storageTest = new HashMap<>();
        storageTest.put(DEFAULT_KEY1, DEFAULT_VALUE1);
        storageTest.put(DEFAULT_KEY2, DEFAULT_VALUE2);
        storageTest.put(DEFAULT_KEY3, DEFAULT_VALUE3);
    }

    @Test
    void storageAddValidData_Ok() {
        storage.put(DEFAULT_KEY1, DEFAULT_VALUE1);
        storage.put(DEFAULT_KEY2, DEFAULT_VALUE2);
        storage.put(DEFAULT_KEY3, DEFAULT_VALUE3);
        assertEquals(storage, storageTest);
    }
}
