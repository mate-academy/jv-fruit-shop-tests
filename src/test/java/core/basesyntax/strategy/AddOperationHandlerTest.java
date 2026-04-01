package core.basesyntax.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddOperationHandlerTest {
    private AddOperationHandler addOperationHandler;

    @BeforeEach
    void setUp() {
        addOperationHandler = new AddOperationHandler();
    }

    @Test
    void apply_validOutput_ok() {
        Assertions.assertEquals(3, addOperationHandler.apply(1, 2));
    }

    @Test
    void apply_currentIsZero_ok() {
        Assertions.assertEquals(2, addOperationHandler.apply(0, 2));
    }

    @Test
    void apply_transactionIsZero_ok() {
        Assertions.assertEquals(1, addOperationHandler.apply(1, 0));
    }

    @Test
    void apply_currentIsMinus_ok() {
        Assertions.assertEquals(1, addOperationHandler.apply(-2, 3));
    }

    @Test
    void apply_transactionIsMinus_ok() {
        Assertions.assertEquals(1, addOperationHandler.apply(4, -3));
    }

    @Test
    void apply_minusOutput_ok() {
        Assertions.assertEquals(-2, addOperationHandler.apply(2, -4));
    }
}
