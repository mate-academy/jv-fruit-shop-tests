package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void add_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.add("banana", -10);
        });
    }

    @Test
    void add_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.add(null, 10);
        });
    }

    @Test
    void remove_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.remove("banana", -10);
        });
    }

    @Test
    void remove_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.remove(null, 10);
        });
    }

    @Test
    void remove_quantityGreaterThanCurrent_notOk() {
        Storage.add("banana", 5);
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.remove("banana", 10);
        });
    }

    @Test
    void add_positiveQuantity_Ok() {
        Storage.add("banana", 10);
        assertEquals(10, Storage.get("banana"));
    }

    @Test
    void remove_validQuantity_Ok() {
        Storage.add("banana", 10);
        Storage.remove("banana", 1);
        assertEquals(9, Storage.get("banana"));
    }

    @Test
    void add_multipleTimesSameFruit_Ok() {
        Storage.add("banana", 10);
        Storage.add("banana", 5);
        assertEquals(15, Storage.get("banana"));
    }

    @Test
    void get_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.get(null);
        });
    }
}
