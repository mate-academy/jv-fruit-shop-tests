package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.strategy.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final int EXPECTED_AMOUNT = 10;
    private static final int AMOUNT = 10;
    private static final int ZERO = 0;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void doCalculation_positiveNumberReturn_Ok() {
        assertTrue(supplyHandler.doCalculation(AMOUNT) > ZERO);
    }

    @Test
    public void doCalculation_checkAmount_Ok() {
        assertEquals(EXPECTED_AMOUNT, supplyHandler.doCalculation(AMOUNT));
    }
}
