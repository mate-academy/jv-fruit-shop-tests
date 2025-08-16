package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperation();
    }

    @Test
    void apply_existingFruit_subtractsQuantity() {
        int result = handler.apply(100, 30);
        assertEquals(70, result);
    }

    @Test
    void apply_newFruit_throwsException() {
        assertThrows(RuntimeException.class, () -> handler.apply(0, 30));
    }

    @Test
    void apply_zeroQuantity_noChange() {
        int result = handler.apply(100, 0);
        assertEquals(100, result);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
