package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @AfterEach
    void afterEachTest() {
        Storage.clearStorage();
    }

    @Test
    void addFruits_nullFruitName_notOK() {
        assertThrows(RuntimeException.class, () -> Storage.addFruits(null, 10));
    }

    @Test
    void addFruits_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.addFruits("", 10));
    }

    @Test
    void addFruits_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.addFruits("banana", -2));
    }

    @Test
    void addFruits_integerOverflow_notOk() {
        Storage.addFruits("banana", Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> Storage.addFruits("banana", 1));
    }

    @Test
    void addFruits_Ok() {
        Storage.addFruits("banana", 0);
        assertEquals(0, Storage.getFruitsBalance("banana"));
        Storage.addFruits("banana", 10);
        assertEquals(10, Storage.getFruitsBalance("banana"));
        Storage.addFruits("banana", 10);
        assertEquals(20, Storage.getFruitsBalance("banana"));

        Storage.addFruits("apple", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruitsBalance("apple"));
    }

    @Test
    void subtractFruits_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.subtractFruits(null, 10));
    }

    @Test
    void subtractFruits_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.subtractFruits("", 10));
    }

    @Test
    void subtractFruits_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.subtractFruits("banana", -2));
    }

    @Test
    void subtractFruits_noSuchFruit_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.subtractFruits("banana", 10));
    }

    @Test
    void subtractFruits_subtractTooMany_notOk() {
        Storage.addFruits("banana", 10);
        assertThrows(RuntimeException.class, () -> Storage.subtractFruits("banana", 11));
    }

    @Test
    void subtractFruits_ok() {
        Storage.addFruits("banana", 10);
        Storage.subtractFruits("banana", 10);
        assertEquals(0, Storage.getFruitsBalance("banana"));

        Storage.addFruits("apple", 10);
        Storage.subtractFruits("apple", 0);
        assertEquals(10, Storage.getFruitsBalance("apple"));
        Storage.subtractFruits("apple", 1);
        assertEquals(9, Storage.getFruitsBalance("apple"));
    }

    @Test
    void setFruitBalance_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.setFruitBalance(null, 10));
    }

    @Test
    void setFruitBalance_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.setFruitBalance("", 10));
    }

    @Test
    void setFruitBalance_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> Storage.setFruitBalance("banana", -2));
    }

    @Test
    void setFruitBalance_ok() {
        Storage.setFruitBalance("banana", 0);
        assertEquals(0, Storage.getFruitsBalance("banana"));
        Storage.setFruitBalance("banana", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruitsBalance("banana"));
    }
}
