package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @Test
    void getStorageFruit_sameInstance_Ok() {
        Map<String, Integer> first = Storage.getStorageFruit();
        Map<String, Integer> second = Storage.getStorageFruit();
        assertSame(first,second);
    }

    @Test
    void getStorageFruit_valid_OK() {
        Storage.getStorageFruit().put("banana", 30);
        assertEquals(30,Storage.getStorageFruit().get("banana"));
    }

    @AfterEach
    void afterEach() {
        Storage.getStorageFruit().clear();
    }
}
