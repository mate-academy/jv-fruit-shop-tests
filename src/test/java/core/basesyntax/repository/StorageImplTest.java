package core.basesyntax.repository;

import static core.basesyntax.TestObjects.APPLE;
import static core.basesyntax.TestObjects.BANANA;
import static core.basesyntax.TestObjects.ORANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
        storage.getAll().clear();
    }

    @Test
    void add_isOk() {
        storage.add(APPLE, 23);
        Integer appleQuantity = storage.getQuantity(APPLE);
        assertEquals(23, appleQuantity);
    }

    @Test
    void getQuantity_isOk() {
        storage.add(BANANA, 11);
        Integer bananaQuantity = storage.getQuantity(BANANA);
        assertEquals(11, bananaQuantity);
    }

    @Test
    void getQuantity_notOk() {
        storage.add(APPLE, 23);
        Integer orange = storage.getQuantity(ORANGE);
        assertNotEquals(23, orange);
    }

    @Test
    void getQuantity_isNull_Ok() {
        Integer orange = storage.getQuantity(ORANGE);
        assertNull(orange);
    }

    @Test
    void getAll_notNull_Ok() {
        assertNotNull(storage.getAll());
    }

    @Test
    void getAll_Ok() {
        storage.add(APPLE, 23);
        storage.add(BANANA, 23);
        storage.add(ORANGE, 4);
        assertEquals(3, storage.getAll().size());
        assertTrue(storage.getAll().containsKey(APPLE));
        assertTrue(storage.getAll().containsKey(BANANA));
        assertTrue(storage.getAll().containsKey(ORANGE));
    }
}
