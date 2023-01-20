package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class PurchaseOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 20;
    private static final int VALID_QUANTITY = 13;
    private static final int EXPECTED_RESULT = VALID_BALANCE - VALID_QUANTITY;
    private OperationCalculator purchaseOperationCalculator = new PurchaseOperationCalculatorImpl();

    @Test
    public void calculatePurchase_isOk() {
        int actual = purchaseOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals("purchase calculate not valid ", actual, EXPECTED_RESULT);
    }
}
