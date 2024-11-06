package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
    }

    @Test
    void storageKeyAfterPut_Ok() {
        Storage.fruitShopStorage.put("apple", 10);
        final boolean actual = Storage.fruitShopStorage.containsKey("apple");
        assertTrue(actual);
    }

    @Test
    void storageValueAfterPut_Ok() {
        Storage.fruitShopStorage.put("apple", 10);
        final boolean actual = Storage.fruitShopStorage.containsValue(10);
        assertTrue(actual);
    }

}
