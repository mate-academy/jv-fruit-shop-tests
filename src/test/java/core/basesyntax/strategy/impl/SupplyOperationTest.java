package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        storage = new StorageImpl();
    }

    @Test
    void apply_increaseFruitQuantity_ok() {
        storage.addEntry("banana", 10);
        supplyOperation.apply("banana", 15, storage);
        assertEquals(25, storage.getQuantity("banana"));
    }

    @Test
    void apply_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply(null, 10, storage));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.apply("banana", -10, storage));
    }
}
