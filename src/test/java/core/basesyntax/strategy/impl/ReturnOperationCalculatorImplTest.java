package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class ReturnOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 100;
    private static final int VALID_QUANTITY = 10;
    private static final int ZERO_BALANCE = 0;
    private static final int ZERO_QUANTITY = 0;
    private static final int EXPECTED_RESULT_FOR_ZERO_BALANCE = ZERO_BALANCE + VALID_QUANTITY;
    private static final int EXPECTED_RESULT_FOR_ZERO_QUANTITY = VALID_BALANCE + ZERO_QUANTITY;
    private static final int EXPECTED_RESULT = VALID_BALANCE + VALID_QUANTITY;
    private OperationCalculator returnOperationCalculator = new ReturnOperationCalculatorImpl();

    @Test
    public void calculateReturn_ok() {
        int actual = returnOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals("return calculate not valid ", actual, EXPECTED_RESULT);
    }

    @Test
    public void calculateReturnZeroBalance_ok() {
        int actual = returnOperationCalculator.calculate(ZERO_BALANCE, VALID_QUANTITY);
        assertEquals("return calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_BALANCE);
    }

    @Test
    public void calculateReturnZeroQuantity_ok() {
        int actual = returnOperationCalculator.calculate(VALID_BALANCE, ZERO_QUANTITY);
        assertEquals("return calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_QUANTITY);
    }
}
