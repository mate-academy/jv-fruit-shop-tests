package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.fruitshop.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFruitStorage().clear();
    }

    @Test
    void testSingletonInstance() {
        Storage anotherInstance = Storage.getInstance();
        assertSame(storage, anotherInstance, "Storage instances should be the same (singleton)");
    }

    @Test
    void testStoragePutAndGet() {
        storage.getFruitStorage().put("apple", 20);
        assertEquals(20, storage.getFruitStorage().get("apple"),
                "Stored and retrieved quantity should be the same");
    }
}
