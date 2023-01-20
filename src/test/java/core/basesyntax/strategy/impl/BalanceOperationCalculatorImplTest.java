package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationCalculator;
import org.junit.Test;

public class BalanceOperationCalculatorImplTest {
    private static final int VALID_BALANCE = 28;
    private static final int VALID_QUANTITY = 13;
    private OperationCalculator balanceOperationCalculator;

    @Test
    public void calculateBalance_isOk() {
        balanceOperationCalculator = new BalanceOperationCalculatorImpl();
        int actual = balanceOperationCalculator.calculate(VALID_BALANCE, VALID_QUANTITY);
        assertEquals(VALID_QUANTITY, actual);
    }
}
