package core.basesyntax.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class StorageTest {
    private static final Storage storage = new Storage();

    @AfterEach
    public void afterEach() {
        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }

    @Test
    void getDefaultValue_FruitDoesntExist_ok() {
        Assertions.assertEquals(0, storage
                .getDataStorageMapOrDefault("banana").get("banana"));
    }

    @Test
    void getDataStorageMapOrDefault_ok() {
        storage.getDataStorageMapOrDefault("apple").put("apple", 30);
        storage.getDataStorageMapOrDefault("orange").put("orange", 40);
        Assertions.assertEquals(30, storage
                .getDataStorageMapOrDefault("apple").get("apple"));
        Assertions.assertEquals(40, storage
                .getDataStorageMapOrDefault("orange").get("orange"));
    }

    @Test
    void getDataStorageMapOrDefault_nullFruit_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.getDataStorageMapOrDefault(null));
    }

    @Test
    void getDataStorageMap_ok() {
        storage.getDataStorageMapOrDefault("apple").put("apple", 42);
        storage.getDataStorageMapOrDefault("orange").put("orange", 64);
        Map<String,Integer> expectedMap = new HashMap<>();
        expectedMap.put("orange", 64);
        expectedMap.put("apple", 42);
        Assertions.assertEquals(expectedMap, storage.getDataStorageMap());
    }
}
