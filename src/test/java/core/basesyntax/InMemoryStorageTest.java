package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryStorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
    }

    @Test
    void putQuantity_ok() {
        storage.putQuantity("banana", 30);
        storage.putQuantity("apple", 80);
        Assertions.assertEquals(30, storage.getQuantity("banana"));
        Assertions.assertEquals(80, storage.getQuantity("apple"));
    }

    @Test
    void putQuantityNullFruit_ok() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.putQuantity(null, 50));
        Assertions.assertTrue(ex.getMessage().contains("Fruit name must not be null"));
    }

    @Test
    void putQuantityEmptyFruit_ok() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.putQuantity("", 50));
        Assertions.assertTrue(ex.getMessage().contains("Fruit name must not be null or empty"));
    }

    @Test
    void putNotPositiveQuantity_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.putQuantity("banana", -203));
        Assertions.assertTrue(ex.getMessage().contains("Quantity must be non-negative"));
    }

    @Test
    void getQuantityNullFruit_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.getQuantity(null));
        Assertions.assertTrue(ex.getMessage().contains("Fruit name must not be null or empty"));
    }

    @Test
    void getQuantityNonExistFruit() {
        Assertions.assertEquals(0, storage.getQuantity("banana"));
    }
}
