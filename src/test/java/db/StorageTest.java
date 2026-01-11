package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage;
    private String fruit;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruit = "banana";
    }

    @Test
    void put_validFruitAndQuantity_Ok() {
        storage.put(fruit, 14);
        assertEquals(14, storage.getAll().get(fruit));
    }

    @Test
    void put_quantityLessThanZero_NotOk() {
        assertThrows(IllegalArgumentException.class, ()
                -> storage.put(fruit, -5));
    }

    @Test
    void put_fruitIsNull_NotOk() {
        assertThrows(IllegalArgumentException.class, ()
                -> storage.put(null, 10));
    }

    @Test
    void add_validIncreaseQuantity_Ok() {
        storage.put(fruit, 5);
        storage.add(fruit, 13);
        assertEquals(18, storage.getAll().get(fruit));
    }

    @Test
    void add_quantityLessThanZero_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.add(fruit,-1));
    }

    @Test
    void subtract_validReductionQuantity_Ok() {
        storage.put(fruit, 26);
        storage.subtract(fruit, 11);
        assertEquals(15, storage.getAll().get(fruit));
    }

    @Test
    void subtract_resultLessZero_NotOk() {
        storage.put(fruit, 10);
        assertThrows(IllegalArgumentException.class, ()
                -> storage.subtract(fruit, 14));
    }

    @Test
    void subtract_quantityLessZero_NotOk() {
        assertThrows(IllegalArgumentException.class,()
                -> storage.subtract(fruit, -10));
    }

    @Test
    void getAll() {
    }
}
