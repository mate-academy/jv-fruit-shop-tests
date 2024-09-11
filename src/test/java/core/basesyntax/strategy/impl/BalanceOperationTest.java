package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        storage = new StorageImpl();
    }

    @Test
    void apply_ShouldAddFruitToStorage() {
        balanceOperation.apply("banana", 20, storage);
        assertEquals(20, storage.getQuantity("banana"));
    }

    @Test
    void apply_ShouldThrowExceptionForNullFruit() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(null, 20, storage));
    }

    @Test
    void apply_ShouldThrowExceptionForEmptyFruit() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(" ", 20, storage));
    }

    @Test
    void apply_ShouldThrowExceptionForNonPositiveQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply("banana", 0, storage));
    }
}
