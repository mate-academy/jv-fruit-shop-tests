package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class PurchaseOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 20;
    private static final int VALID_QUANTITY = 13;
    private static final int ZERO_BALANCE = 0;
    private static final int ZERO_QUANTITY = 0;
    private static final int EXPECTED_RESULT = VALID_BALANCE - VALID_QUANTITY;
    private static final int EXPECTED_RESULT_FOR_ZERO_BALANCE = ZERO_BALANCE - VALID_QUANTITY;
    private static final int EXPECTED_RESULT_FOR_ZERO_QUANTITY = VALID_BALANCE - ZERO_QUANTITY;
    private OperationCalculator purchaseOperationCalculator = new PurchaseOperationCalculatorImpl();

    @Test
    public void calculatePurchase_ok() {
        int actual = purchaseOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals("purchase calculate not valid ", actual, EXPECTED_RESULT);
    }

    @Test
    public void calculatePurchaseZeroBalance_ok() {
        int actual = purchaseOperationCalculator.calculate(ZERO_BALANCE, VALID_QUANTITY);
        assertEquals("purchase calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_BALANCE);
    }

    @Test
    public void calculatePurchaseZeroQuantity_ok() {
        int actual = purchaseOperationCalculator.calculate(VALID_BALANCE, ZERO_QUANTITY);
        assertEquals("purchase calculate not valid ", actual, EXPECTED_RESULT_FOR_ZERO_QUANTITY);
    }
}
