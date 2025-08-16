package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperation();
    }

    @Test
    void apply_existingFruit_addsQuantity() {
        int result = handler.apply(100, 20);
        assertEquals(120, result);
    }

    @Test
    void apply_newFruit_createsEntry() {
        int result = handler.apply(0, 20);
        assertEquals(20, result);
    }

    @Test
    void apply_zeroQuantity_noChange() {
        int result = handler.apply(100, 0);
        assertEquals(100, result);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
