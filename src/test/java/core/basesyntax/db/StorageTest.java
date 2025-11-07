package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    @Test
    void setQuantity_ok() {
        Storage.setQuantity("banana", 100);
        assertEquals(100, Storage.getQuantity("banana"));
    }

    @Test
    void addQuantity_ok() {
        Storage.setQuantity("apple", 10);
        Storage.addQuantity("apple", 5);
        assertEquals(15, Storage.getQuantity("apple"));
    }

    @Test
    void getQuantity_notExists_returnsZero() {
        assertEquals(0, Storage.getQuantity("orange"));
    }

    @Test
    void clear_removesAllEntries() {
        Storage.setQuantity("banana", 10);
        assertFalse(Storage.getAll().isEmpty());
        Storage.clear();
        assertTrue(Storage.getAll().isEmpty());
    }
}
