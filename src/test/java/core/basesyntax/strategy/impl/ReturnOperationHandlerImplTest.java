package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReturnOperationHandlerImplTest {
    private static final int NORMAL_VALUE = 10;
    private static final int ZERO_VALUE = 0;
    private static final int LESS_THEN_VALUE = -5;
    private static final FruitTransaction NORMAL_VALUE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, NORMAL_VALUE);
    private static final FruitTransaction ZERO_VALUE_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, ZERO_VALUE);
    private static final FruitTransaction LESS_THEN_ZERO_TRANSACTION =
            new FruitTransaction(Operation.BALANCE, null, LESS_THEN_VALUE);
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new BalanceOperationHandlerImpl();
    }

    @Test
    void balanceOperationHandler_validValues_Ok() {
        int expected = NORMAL_VALUE;
        int actual = returnHandler.count(NORMAL_VALUE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void balanceOperationHandler_zeroValue_Ok() {
        int expected = ZERO_VALUE;
        int actual = returnHandler.count(ZERO_VALUE_TRANSACTION);

        assertEquals(expected, actual);
    }

    @Test
    void balanceOperationHandler_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                returnHandler.count(LESS_THEN_ZERO_TRANSACTION));
    }
}