package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerImplTest {
    private static final int NORMAL_VALUE = 10;
    private static final int ZERO_VALUE = 0;
    private static final int LESS_THEN_VALUE = -5;
    private static final FruitTransaction NORMAL_VALUE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, LESS_THEN_VALUE);
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseOperationHandlerImpl();
    }

    @Test
    void purchaseOperationHandler_validValues_Ok() {
        int expected = -NORMAL_VALUE;
        int actual = purchaseHandler.count(NORMAL_VALUE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void purchaseOperationHandler_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = purchaseHandler.count(ZERO_VALUE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void purchaseOperationHandler_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.count(LESS_THEN_ZERO_TRANSACTION));
    }
}
