package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int EXPECTED_AMOUNT = -10;
    private static final int AMOUNT = 10;
    private static final int ZERO = 0;
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @Test
    public void doCalculation_negativeNumberReturn_Ok() {
        assertTrue(purchaseHandler.doCalculation(AMOUNT) < ZERO);
    }

    @Test
    public void doCalculation_checkAmount_Ok() {
        assertEquals(EXPECTED_AMOUNT, purchaseHandler.doCalculation(AMOUNT));
    }
}
