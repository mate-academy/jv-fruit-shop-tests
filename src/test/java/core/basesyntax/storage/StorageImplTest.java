package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StorageImplTest {

    @Test
    void put_validFruit_ok() {
        Storage expected = new StorageImpl();
        expected.put("banana", 100);

        Storage actual = new StorageImpl();
        actual.put("banana", 100);
        assertEquals(expected, actual);
    }

    @Test
    void remove_insufficientQuantity_notOk() {
        Storage actual = new StorageImpl();
        actual.put("banana", 0);
        assertThrows(IllegalArgumentException.class, () ->
                actual.remove("banana", 10));
    }

    @Test
    void remove_missingFruit_notOk() {
        Storage actual = new StorageImpl();
        assertThrows(IllegalArgumentException.class, () ->
                actual.remove("banana", 100));
    }

    @Test
    void remove_exactQuantity_setsQuantityToZero() {
        Storage storage = new StorageImpl();
        storage.put("banana", 100);
        storage.remove("banana", 100);
        assertEquals(0, storage.getQuantity("banana"));
    }

    @Test
    void getAll_validStorage_ok() {
        Storage actual = new StorageImpl();
        actual.put("banana", 100);

        Storage expected = new StorageImpl();
        expected.put("banana", 100);
        assertEquals(expected.getAll(), actual.getAll());
    }

    @Test
    void getQuantity_whenFruitsNotExist_notOk() {
        Storage actual = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> actual.getQuantity("banana"));
    }

    @Test
    void put_existingFruit_replacesQuantity_ok() {
        Storage actual = new StorageImpl();
        actual.put("banana", 100);
        actual.put("banana", 50);
        assertEquals(50, actual.getQuantity("banana"));
    }

    @Test
    void put_nullFruit_notOk() {
        Storage storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class, () ->
                storage.put(null, 100));
    }

    @Test
    void add_newFruit_ok() {
        Storage actual = new StorageImpl();
        actual.add("banana", 100);
        assertEquals(100, actual.getQuantity("banana"));
    }

    @Test
    void add_nullFruit_notOk() {
        Storage storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> storage.add(null, 100));
    }
}
