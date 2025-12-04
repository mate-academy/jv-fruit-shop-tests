package core.basesyntax.db;

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
        fruit = "apple";
    }

    @Test
    void put_validFruit_isOk() {
        storage.put(fruit,10);
        assertEquals(10,storage.getAll().get(fruit));
    }

    @Test
    void put_fruitIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.put(null,1));
    }

    @Test
    void put_quantityIsNegative_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.put(fruit,-1));
    }

    @Test
    void add_validQuantityIncreased_isOk() {
        storage.put(fruit,10);
        storage.add(fruit,5);
        assertEquals(15,storage.getAll().get(fruit));
    }

    @Test
    void add_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.add(fruit,-1));
    }

    @Test
    void subtract_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.subtract(fruit,-1));
    }

    @Test
    void subtract_negativeResult_notOk() {
        storage.put(fruit,3);
        assertThrows(IllegalArgumentException.class,
                () -> storage.subtract(fruit,4));
    }

    @Test
    void subtract_resultIsValid_isOk() {
        storage.put(fruit,10);
        storage.subtract(fruit,5);
        assertEquals(5,storage.getAll().get(fruit));
    }

    @Test
    void subtract_fruitNotExisting_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> storage.subtract("banana", 1));
    }
}
