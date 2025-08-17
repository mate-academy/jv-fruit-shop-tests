package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperation();
        Storage.getFruitStorage().clear();
    }

    @Test
    void apply_newFruit_setsBalance() {
        int result = handler.apply(0, 100);
        assertEquals(100, result);
    }

    @Test
    void apply_existingFruit_replacesBalance() {
        int result = handler.apply(50, 100);
        assertEquals(100, result);
    }

    @Test
    void apply_zeroQuantity_setsZero() {
        int result = handler.apply(100, 0);
        assertEquals(0, result);
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        assertThrows(RuntimeException.class, () -> handler.apply(100, -10));
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }
}
