package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.exception.InsufficientQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        storage = new StorageImpl();
    }

    @Test
    void apply_increaseFruitQuantity_ok() {
        storage.addEntry("banana", 10);
        returnOperation.apply("banana", 5, storage);
        assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    void apply_notEnoughQuantity_notOk() {
        storage.addEntry("banana", 5);
        assertThrows(InsufficientQuantityException.class,
                () -> returnOperation.apply("banana", 10, storage));
    }

    @Test
    void apply_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(null, 5, storage));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply("banana", -5, storage));
    }
}
