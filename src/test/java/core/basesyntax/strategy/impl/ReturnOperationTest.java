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
    void apply_ShouldIncreaseFruitQuantity() {
        storage.addEntry("banana", 10);
        returnOperation.apply("banana", 5, storage);
        assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    void apply_ShouldThrowExceptionForNotEnoughQuantity() {
        storage.addEntry("banana", 5);
        assertThrows(InsufficientQuantityException.class,
                () -> returnOperation.apply("banana", 10, storage));
    }

    @Test
    void apply_ShouldThrowExceptionForNullFruit() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply(null, 5, storage));
    }

    @Test
    void apply_ShouldThrowExceptionForNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.apply("banana", -5, storage));
    }
}
