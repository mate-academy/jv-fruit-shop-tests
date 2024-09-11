package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.exception.InsufficientQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        storage = new StorageImpl();
    }

    @Test
    void apply_decreaseFruitQuantity_ok() {
        storage.addEntry("banana", 20);
        purchaseOperation.apply("banana", 5, storage);
        assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    void apply_insufficientQuantity_notOk() {
        storage.addEntry("banana", 5);
        assertThrows(InsufficientQuantityException.class,
                () -> purchaseOperation.apply("banana", 10, storage));
    }

    @Test
    void apply_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply(null, 5, storage));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.apply("banana", -5, storage));
    }
}
