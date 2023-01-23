package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class SupplyOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 20;
    private static final int VALID_QUANTITY = 100;
    private static final int ZERO_BALANCE = 0;
    private static final int ZERO_QUANTITY = 0;
    private static final int EXPECTED_RESULT = VALID_BALANCE + VALID_QUANTITY;
    private static final int EXPECTED_RESULT_FOR_ZERO_BALANCE = ZERO_BALANCE + VALID_QUANTITY;
    private static final int EXPECTED_RESULT_FOR_ZERO_QUANTITY = VALID_BALANCE + ZERO_QUANTITY;
    private OperationCalculator returnOperationCalculator = new ReturnOperationCalculatorImpl();

    @Test
    public void calculateSupply_isOk() {
        int actual = returnOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals("supply calculate not valid ", actual, EXPECTED_RESULT);
    }

    @Test
    public void calculateSupplyZeroBalance_isOk() {
        int actual = returnOperationCalculator.calculate(ZERO_BALANCE, VALID_QUANTITY);
        assertEquals("supply calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_BALANCE);
    }

    @Test
    public void calculateSupplyZeroQuantity_isOk() {
        int actual = returnOperationCalculator.calculate(VALID_BALANCE, ZERO_QUANTITY);
        assertEquals("supply calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_QUANTITY);
    }
}
