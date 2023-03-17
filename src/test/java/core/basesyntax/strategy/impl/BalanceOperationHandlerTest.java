package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest extends Assert {
    private static final int EXPECTED_AMOUNT = 10;
    private static final int AMOUNT = 10;
    private static final int ZERO = 0;
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void doCalculation_positiveNumberReturn_Ok() {
        assertTrue(balanceHandler.doCalculation(AMOUNT) > ZERO);
    }

    @Test
    public void doCalculation_checkAmount_Ok() {
        assertEquals(EXPECTED_AMOUNT, balanceHandler.doCalculation(AMOUNT));
    }
}
