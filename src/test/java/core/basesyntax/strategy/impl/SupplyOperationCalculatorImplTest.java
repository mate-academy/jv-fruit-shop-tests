package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class SupplyOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 20;
    private static final int VALID_QUANTITY = 100;
    private static final int EXPECTED_RESULT = VALID_BALANCE + VALID_QUANTITY;
    private OperationCalculator returnOperationCalculator = new ReturnOperationCalculatorImpl();

    @Test
    public void calculateSupply_isOk() {
        int actual = returnOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals("supply calculate not valid ", actual, EXPECTED_RESULT);
    }
}
