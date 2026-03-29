package core.basesyntax.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    void apply_validOutput_ok() {
        Assertions.assertEquals(4, purchaseHandler.apply(8, 4));
    }

    @Test
    void apply_outputIsZero_ok() {
        Assertions.assertEquals(0, purchaseHandler.apply(4, 4));
    }

    @Test
    void apply_transactionIsZero_ok() {
        Assertions.assertEquals(2, purchaseHandler.apply(2, 0));
    }

    @Test
    void apply_currentAndTransactionIsZero_ok() {
        Assertions.assertEquals(0, purchaseHandler.apply(0, 0));
    }

    @Test
    void apply_currentIsLowerThanTransactionValue_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> purchaseHandler.apply(2, 4));
    }
}
