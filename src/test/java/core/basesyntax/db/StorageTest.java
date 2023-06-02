package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    void fruitMap_emptyAtTheStart_ok() {
        assertTrue(Storage.fruitMap.isEmpty());
    }

    @Test
    void fruitMap_validData_ok() {
        Storage.fruitMap.put("apple", 10);
        assertEquals(1, Storage.fruitMap.size());
        assertTrue(Storage.fruitMap.containsKey("apple"));
        assertEquals(10, Storage.fruitMap.get("apple"));
    }
}
